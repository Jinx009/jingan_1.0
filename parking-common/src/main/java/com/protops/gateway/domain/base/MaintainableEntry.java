package com.protops.gateway.domain.base;

import org.apache.commons.lang.ObjectUtils;

class MaintainableEntry<T> {

	private T object;
	private long createTime;
	private int accessCount;

	public MaintainableEntry(T object) {
		this.object = object;
		this.createTime = System.currentTimeMillis();
		this.accessCount = 0;
	}

	public boolean isTimeout(long timeout) {
		if (timeout > 0) {
			return (System.currentTimeMillis() - this.createTime) >= timeout;
		}
		return false;
	}

	public boolean isExceedMaxAccessCount(int maxAccessCount) {
		if (maxAccessCount > 0) {
			return accessCount >= maxAccessCount;
		}
		return false;
	}

	public void increaseAccessCount() {
		++accessCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MaintainableEntry<T> other = (MaintainableEntry<T>) obj;
		return ObjectUtils.equals(object, other.object);
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(int accessCount) {
		this.accessCount = accessCount;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
}
