package com.protops.gateway.dao.device;

import com.protops.gateway.domain.device.TianyiIoTDevice;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TianyiIoTDeviceDao extends HibernateBaseDao<TianyiIoTDevice,Integer> {

    public TianyiIoTDevice findByMac(String mac){
        String hql = " FROM TianyiIoTDevice WHERE mac = ? ";
        return findUnique(hql,mac);

    }

}
