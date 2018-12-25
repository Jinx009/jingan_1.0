package com.protops.gateway.dao.log;

import com.protops.gateway.domain.log.SensorDeviceLog;
import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.util.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String,Object> findByMacAndDate(String mac){
        String hql = "SELECT mac AS mac,id,create_time AS createTime,vol  FROM tbl_sensor_devicelog WHERE mac = '"+mac+"' and dif is not null  order by id desc limit 20";
        List<SensorDeviceLog> list = findBySql(hql, SensorDeviceLog.class);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("a",0.00);
        map.put("b",null);
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
            SensorDeviceLog sensorDeviceLog2 = list.get(0);
            map.put("a",String.valueOf(num/size));
            map.put("b",sensorDeviceLog2.getCreateTime());
        }
       return map;
    }

}
