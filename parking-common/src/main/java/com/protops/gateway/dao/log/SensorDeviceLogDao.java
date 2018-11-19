package com.protops.gateway.dao.log;

import com.protops.gateway.domain.log.SensorDeviceLog;
import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.util.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jinx on 3/24/17.
 */
@Repository
@Transactional
public class SensorDeviceLogDao extends HibernateBaseDao<SensorDeviceLog,Integer>{


    public List<SensorDeviceLog> getNearMax(String mac,String dateStr){
        String hql = "SELECT mac AS mac,id,create_time AS createTime,battery_voltage AS batteryVoltage FROM tbl_sensor_devicelog WHERE mac = '"+mac+"' AND  create_time>='"+dateStr+"' order by battery_voltage desc ";
        return findBySql(hql, SensorDeviceLog.class);
    }

    public String findByMacAndDate(String mac){
        String hql = "SELECT mac AS mac,id,create_time AS createTime,vol  FROM tbl_sensor_devicelog WHERE mac = '"+mac+"' and dif is not null  order by id desc limit 20";
        List<SensorDeviceLog> list = findBySql(hql, SensorDeviceLog.class);
        double num = 0.00;
        int size = 0;
        if(list!=null&&!list.isEmpty()){
            for(SensorDeviceLog sensorDeviceLog:list){
                if(StringUtils.isNotBlank(sensorDeviceLog.getVol())){
                    double b = Double.valueOf(sensorDeviceLog.getVol());
                    if(b!=0){
                        size++;
                        num = num+b;
                    }
                }
            }
            return String.valueOf(num/size);
        }
       return "0";
    }

}
