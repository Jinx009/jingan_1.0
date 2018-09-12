package com.protops.gateway.dao.log;

import com.protops.gateway.domain.log.SensorInOutMoneyLog;
import com.protops.gateway.util.HibernateBaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SensorInOutMoneyLogDao  extends HibernateBaseDao<SensorInOutMoneyLog,Integer> {

    private static final Logger logger = LoggerFactory.getLogger(SensorInOutMoneyLogDao.class);

    public List<SensorInOutMoneyLog> getMoney(Integer areaId, String dateStr){
        String sql = "SELECT area_id AS areaId,happen_time AS happenTime,id,money FROM tbl_sensor_inoutmoneylog WHERE area_id = "+areaId+"  AND  happen_time ='"+dateStr+"' ";
        return findBySql(sql, SensorInOutMoneyLog.class);
    }

    public List<SensorInOutMoneyLog> getMoneyMonth(Integer areaId, String dateStr,String endStr){
        String sql = "SELECT area_id AS areaId,happen_time AS happenTime ,id,money FROM tbl_sensor_inoutmoneylog WHERE area_id = "+areaId+"  AND  happen_time >='"+dateStr+"' AND  happen_time <='"+endStr+"'";
        return findBySql(sql, SensorInOutMoneyLog.class);
    }

}
