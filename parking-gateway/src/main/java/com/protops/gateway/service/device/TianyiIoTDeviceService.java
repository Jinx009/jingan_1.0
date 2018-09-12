package com.protops.gateway.service.device;

import com.protops.gateway.dao.device.TianyiIoTDeviceDao;
import com.protops.gateway.domain.device.TianyiIoTDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TianyiIoTDeviceService  {

    @Autowired
    private TianyiIoTDeviceDao tianyiIoTDeviceDao;

    public TianyiIoTDevice findByMac(String mac){
        return tianyiIoTDeviceDao.findByMac(mac);
    }

}
