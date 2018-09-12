package com.protops.gateway.domain.base;

import com.protops.gateway.exception.AccessCountExceedException;
import com.protops.gateway.exception.AccessTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MaintainableMap<K, V> implements Serializable {

    private static final long serialVersionUID = -8324822781253767560L;
    //private static final int DEFAULT_PURGE_SIZE = 2000;
    //private static final int SAFE_PURGE_SIZE = 500;
    private static final Logger logger = LoggerFactory.getLogger(MaintainableMap.class);

    private int maxCapacity = 64;
    private float balanceFactor;
    private long timeout;
    private int maxAccessCount;
    private boolean refreshTimeOnAccess;

    //回收是否正在进行
    private Boolean purging = false;

    private ConcurrentMap<K, MaintainableEntry<V>> storage;

    private int purgeThreshold = 0;

    private int purgeSize;

    protected MaintainableMap() {
        this(10000, 0.75f, 5 * 60 * 1000, 0);
    }

    /**
     * @param maxCapacity
     * @param balanceFactor
     * @param timeout        小于等于0 不参考
     * @param maxAccessCount 小于等于0 不参考
     */
    public MaintainableMap(int maxCapacity, float balanceFactor, long timeout, int maxAccessCount, boolean refreshTimeOnAccess) {
        this(maxCapacity, balanceFactor, timeout, maxAccessCount, refreshTimeOnAccess, maxCapacity >> 3);
    }

    public MaintainableMap(int maxCapacity, float balanceFactor, long timeout, int maxAccessCount, boolean refreshTimeOnAccess, int purgeSize) {
        super();
        this.maxCapacity = maxCapacity < 64 ? 64 : maxCapacity;
        this.balanceFactor = balanceFactor;
        this.timeout = timeout;
        this.maxAccessCount = maxAccessCount;
        this.refreshTimeOnAccess = refreshTimeOnAccess;
        this.storage = new ConcurrentHashMap<K, MaintainableEntry<V>>(maxCapacity, balanceFactor);
        //不能频繁进行回收
        if (purgeSize < this.maxCapacity >> 4 || purgeSize > this.maxCapacity >> 2) {
            this.purgeSize = this.maxCapacity >> 3;
        } else {
            this.purgeSize = purgeSize;
        }
    }

    public MaintainableMap(int maxCapacity, float balanceFactor, long timeout, int maxAccessCount) {
        this(maxCapacity, balanceFactor, timeout, maxAccessCount, false);
    }

    protected boolean needsPurge() {

        if (purging) {
            logger.info("purging operation is running");
            return false;
        }

        if (purgeThreshold > purgeSize) {
            purgeThreshold = 0;
            return true;
        } else {
            purgeThreshold++;
            return false;
        }
    }

    /**
     * clear out-of-date object in this map
     */
    protected void purge() {

        if (!purging) {

            synchronized (purging) {

                try {
                    purging = true;


                    int originSize = storage.size();
                    long startTime = System.currentTimeMillis();
                    Iterator<Map.Entry<K, MaintainableEntry<V>>> iterator = storage.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<K, MaintainableEntry<V>> entry = iterator.next();
                        if (entry.getValue().isTimeout(timeout) || entry.getValue().isExceedMaxAccessCount(maxAccessCount)) {
                            iterator.remove();
                        }
                    }

                    logger.info("purging storage size from {} to {} cost " + (System.currentTimeMillis() - startTime) + "ms", originSize, storage.size());

                } finally {
                    purging = false;
                }
            }
        } else {
            logger.info("purging operation is running");
        }
    }

    public void clear() {
        storage.clear();
        purgeThreshold = 0;
    }

    public int size() {
//        if (needsPurge()) {
//            purge();
//        }
        return storage.size();
    }

    public boolean isEmpty() {
//        if (needsPurge()) {
//            purge();
//        }
        return storage.isEmpty();
    }

    public void refreshTime(K key) {
        if (storage.containsKey(key)) {
            storage.get(key).setCreateTime(System.currentTimeMillis());
        }
    }

    public boolean contains(V value) {
//		if (needsPurge()) {
//			purge();
//		}
        return storage.containsValue(new MaintainableEntry<V>(value));
    }

    public boolean containsKey(K key) {
        return containsKey(key, true);
    }

    public boolean containsKey(K key, boolean validate) {
        if (validate) {
            try {
                checkTimeout(key);
            } catch (AccessTimeoutException e) {
                return false;
            }
            try {
                checkAccessCount(key);
            } catch (AccessCountExceedException e) {
                return false;
            }
        }
        return storage.containsKey(key);
    }

    public V get(K key) throws AccessTimeoutException, AccessCountExceedException {
        return get(key, true);
    }

    public V get(K key, boolean increaseAccessCount) throws AccessTimeoutException, AccessCountExceedException {
        checkTimeout(key);
        checkAccessCount(key);
        if (storage.containsKey(key)) {
            if (increaseAccessCount) {
                storage.get(key).increaseAccessCount();
            }
            if (this.refreshTimeOnAccess) {
                refreshTime(key);
            }
            return storage.get(key).getObject();
        }
        return null;
    }

    /**
     * Only get value, ignore timeout and access count
     */
    public V getValue(K key) {
        if (storage.containsKey(key)) {
            return storage.get(key).getObject();
        }
        return null;
    }

    public V put(K key, V value) {
        return put(key, value, false, false);
    }

    public V put(K key, V value, boolean keepCreateTime, boolean keepAccessCount) {
        if (needsPurge()) {
            purge();
        }
        if (storage.containsKey(key)) {
            MaintainableEntry<V> oldValue = storage.get(key);
            V returnValue = oldValue.getObject();
            oldValue.setObject(value);
            if (!keepCreateTime) {
                oldValue.setCreateTime(System.currentTimeMillis());
            }
            if (!keepAccessCount) {
                oldValue.setAccessCount(0);
            }
            return returnValue;
        } else {
            storage.put(key, new MaintainableEntry<V>(value));
            return null;
        }
    }

    public V remove(K key) {
        if (needsPurge()) {
            purge();
        }
        try {
            checkTimeout(key);
        } catch (AccessTimeoutException e) {
            return null;
        }
        try {
            checkAccessCount(key);
        } catch (AccessCountExceedException e) {
            return null;
        }
        MaintainableEntry<V> oldValue = storage.remove(key);
        return oldValue == null ? null : oldValue.getObject();
    }

    public void putAll(Map<? extends K, ? extends V> t) {
        if (needsPurge()) {
            purge();
        }
        if (t == null) {
            return;
        }
        for (Map.Entry<? extends K, ? extends V> entry : t.entrySet()) {
            storage.put(entry.getKey(), new MaintainableEntry<V>(entry.getValue()));
        }
    }

    /**
     * @return
     */
    @Deprecated
    public Set<K> keySet() {
//        if (needsPurge()) {
//            purge();
//        }
        return new HashSet<K>(storage.keySet());
    }

    public Long getCreateTime(K key) {
        if (storage.containsKey(key)) {
            //the storage is not locked, this key may be removed right between check and get
            try {
                return storage.get(key).getCreateTime();
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public int getAccessCount(K key) {
        if (storage.containsKey(key)) {
            return storage.get(key).getAccessCount();
        } else {
            return -1;
        }
    }

    private void checkTimeout(K key) throws AccessTimeoutException {
        if (storage.containsKey(key) && storage.get(key).isTimeout(timeout)) {
            storage.remove(key);
            throw new AccessTimeoutException("Access Timeout for key=" + key);
        }
    }

    private void checkAccessCount(K key) throws AccessCountExceedException {
        if (storage.containsKey(key) && storage.get(key).isExceedMaxAccessCount(maxAccessCount)) {
            storage.remove(key);
            throw new AccessCountExceedException("Access Count Exceed for key=" + key);
        }
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public float getBalanceFactor() {
        return balanceFactor;
    }

    public void setBalanceFactor(float balanceFactor) {
        this.balanceFactor = balanceFactor;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public int getMaxAccessCount() {
        return maxAccessCount;
    }

    public void setMaxAccessCount(int maxAccessCount) {
        this.maxAccessCount = maxAccessCount;
    }

    public boolean isRefreshTimeOnAccess() {
        return refreshTimeOnAccess;
    }

    public void setRefreshTimeOnAccess(boolean refreshTimeOnAccess) {
        this.refreshTimeOnAccess = refreshTimeOnAccess;
    }
}
