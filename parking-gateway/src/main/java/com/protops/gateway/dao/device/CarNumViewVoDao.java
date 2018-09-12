package com.protops.gateway.dao.device;

import com.protops.gateway.domain.device.CarNumViewVo;
import com.protops.gateway.util.HibernateBaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jinx on 3/31/17.
 */
@Repository
public class CarNumViewVoDao extends HibernateBaseDao<CarNumViewVo,Integer>{

    private static final Logger logger = LoggerFactory.getLogger(CarNumViewVoDao.class);


    public static final String BASE_SQL = " SELECT "+
            " sum(a.car_num)AS nums,"+
            " a.direction AS direction,"+
            " a.direction_on_lid AS directionOnLid"+
            " FROM tbl_intersectionsensor_operationlog a  WHERE a.pos = 0  AND  a.in_or_out = 1  ";

    public static final String END_SQL = " GROUP BY  a.lid";


    public List<CarNumViewVo> getData(Integer location,Integer area,String startDate,String endDate){
        String sql = BASE_SQL+getSensorSql(location,area)+getOperationSql(startDate, endDate)+END_SQL;
        logger.warn("CarNumViewVoDao.getData sql:{}",sql);
        return findBySql(sql, CarNumViewVo.class);
    }


    private String getOperationSql(String startDate,String endDate){
        return " AND a.test_time>= '"+startDate+"' AND a.test_time<= '"+endDate+"' ";
    }

    private String getSensorSql(Integer location,Integer area){
        String sql = "";
        if(area!=null&&area!=0){
            sql += " AND a.area_id= "+area;
        }
        return sql;
    }
}
