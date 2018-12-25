package com.protops.gateway.service.log;


import com.protops.gateway.dao.log.SensorDeviceLogDao;
import com.protops.gateway.domain.log.SensorDeviceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SensorDeviceLogService {


    @Autowired
    private SensorDeviceLogDao sensorDeviceLogDao;

    public SensorDeviceLog getNearMax(String mac,String dateStr){
        List<SensorDeviceLog> list = sensorDeviceLogDao.getNearMax(mac,dateStr);
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public Map<String,Object> findByMacAndDate(String mac){
        return sensorDeviceLogDao.findByMacAndDate(mac);
    }

    public void save(SensorDeviceLog sensorDeviceLog){
        sensorDeviceLogDao.save(sensorDeviceLog);
    }

}
