package com.protops.gateway.dao.log;

import com.protops.gateway.domain.log.SensorInOutLog;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jinx on 4/13/17.
 */
@Repository
public class SensorInOutLogDao extends HibernateBaseDao<SensorInOutLog,Integer>{


    public List<SensorInOutLog> getByAreaId(Integer areaId,String dateStr){
        String hql = "SELECT area_id AS areaId,description,id,times,in_time AS inTime,out_time AS outTime,mac FROM tbl_sensor_inoutlog WHERE area_id = "+areaId+" and times is not null AND  date_format(create_time, '%Y-%m-%d')='"+dateStr+"' ";
        return findBySql(hql, SensorInOutLog.class);
    }


    public List<SensorInOutLog> findUse(Integer areaId,String startStr,String endStr){
        String hql = "SELECT area_id AS areaId,description,id,times,in_time AS inTime,out_time AS outTime,mac FROM tbl_sensor_inoutlog WHERE area_id = "+areaId+"  AND  ((in_time>='"+startStr+"' and  in_time<='"+endStr+"') and (out_time>='"+startStr+"' and  out_time<='"+endStr+"') ) ";
        return findBySql(hql, SensorInOutLog.class);
    }

    public List<SensorInOutLog> getByAreaIdMonth(Integer areaId,String dateStr,String startDate){
        String hql = "SELECT area_id AS areaId,description,id,times,in_time AS inTime,out_time AS outTime,mac FROM tbl_sensor_inoutlog WHERE area_id = "+areaId+" and times is not null AND  date_format(create_time, '%Y-%m-%d')<='"+dateStr+"' AND  date_format(create_time, '%Y-%m-%d')>='"+startDate+"' ";
        return findBySql(hql, SensorInOutLog.class);
    }

    public List<SensorInOutLog> getByAreaIdSimpleMonth(Integer areaId,String dateStr){
        String hql = "SELECT area_id AS areaId,description,id,times,in_time AS inTime,out_time AS outTime,mac FROM tbl_sensor_inoutlog WHERE area_id = "+areaId+" and times is not null AND  MONTH(create_time) ='"+dateStr+"' ";
        return findBySql(hql, SensorInOutLog.class);
    }

    public SensorInOutLog findByLogId(String logId){
        String hql = " FROM SensorInOutLog WHERE logId = ? ";
        List<SensorInOutLog> list = find(hql,logId);
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

}
