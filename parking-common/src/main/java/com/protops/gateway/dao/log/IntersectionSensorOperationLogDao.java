package com.protops.gateway.dao.log;

import com.protops.gateway.domain.log.IntersectionSensorOperationLog;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by jinx on 3/13/17.
 */
@Repository
@Transactional
public class IntersectionSensorOperationLogDao extends HibernateBaseDao<IntersectionSensorOperationLog,Integer>{

    /**
     * 获取日志同步最新时间
     * @return
     */
    public String getSycNewTime(){
        String sql = " SELECT create_time AS createTime,test_time AS testTime,id  FROM tbl_intersectionsensor_operationlog WHERE create_time = '2016-01-01 01:01:01' ORDER BY test_time DESC LIMIT 1 ";
        List<IntersectionSensorOperationLog> list = findBySql(sql,IntersectionSensorOperationLog.class);
        if(list!=null&&!list.isEmpty()){
            IntersectionSensorOperationLog intersectionSensorOperationLog = list.get(0);
            return String.valueOf(intersectionSensorOperationLog.getTestTime().getTime());
        }
        return "";
    }

}
