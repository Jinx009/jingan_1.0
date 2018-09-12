package com.protops.gateway.dao.device;

import com.protops.gateway.domain.device.CarNumVo;
import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jinx on 3/29/17.
 */
@Repository
public class CarNumVoDao extends HibernateBaseDao<CarNumVo,Integer> {

    private static final Logger logger = LoggerFactory.getLogger(CarNumVoDao.class);

    private static final String BASE_ONE_SQL = " SELECT " +
            " concat(date_format(test_time, '%Y-%m-%d %H:'),floor(date_format(test_time, '%i') / 2) )AS sort," +
            " test_time AS date," +
            " sum(car_num)AS nums" +
            " FROM tbl_intersectionsensor_operationlog " +
            " WHERE  pos = 0 AND in_or_out = 1 " ;
    private static final String IDS_SQL = " AND mac in ( SELECT mac FROM tbl_intersectionsensor WHERE rec_st = 1 AND pos = 0 AND in_or_out = 1 AND location =  ";
    private static final String END_SQL = " GROUP BY sort ORDER BY date ";
    private static final String BASE_TWO_SQL = " SELECT "+
            " DATE_FORMAT(test_time, '%Y%m%d') as sort,"+
            " test_time AS date, sum(car_num)AS nums"+
            " FROM tbl_intersectionsensor_operationlog WHERE   ";

    /**
     * 当天数据
     * @param location
     * @param area
     * @param lid
     * @param dateStr
     * @return
     */
    public List<CarNumVo> getCarNumOneDay(Integer location,Integer area,Integer lid,String dateStr){
        String dateSql = "";
        if(StringUtils.isNotBlank(dateStr)){
            dateSql = " AND date_format(test_time, '%Y-%m-%d')= '"+dateStr+"' ";
        }else{
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateSql = " AND date_format(test_time, '%Y-%m-%d')= '"+simpleDateFormat.format(date)+"' ";
        }
        String sql = BASE_ONE_SQL+dateSql+getOneSql(location,area,lid)+END_SQL;
        logger.warn(sql);
        List<CarNumVo> list = findBySql(sql,CarNumVo.class);
        return list;
    }

    /**
     * 多天数据
     * @param location
     * @param area
     * @param lid
     * @param dateStr
     * @param endDate
     * @return
     */
    public List<CarNumVo> getCarNumManyDay(Integer location,Integer area,Integer lid,String dateStr,String endDate){
        String dateSql = " AND  (date_format(test_time, '%Y-%m-%d')<='"+dateStr+"') AND  (date_format(test_time, '%Y-%m-%d')>= '"+endDate+"') ";
        String sql = BASE_TWO_SQL+dateSql+getOneSql(location,area,lid)+END_SQL;
        logger.warn(sql);
        List<CarNumVo> list = findBySql(sql,CarNumVo.class);
        return list;
    }

    /**
     * 判断条件
     * @param location
     * @param area
     * @param lid
     * @return
     */
    private String getOneSql(Integer location,Integer area,Integer lid){
//        String sql = IDS_SQL+location;
        String sql = "";
        if(area!=null&&!"".equals(area)&&area!=0){
            sql = sql+" AND area_id = "+area;
        }
        if(lid!=null&&!"".equals(lid)&&lid!=0){
            sql = sql+" AND lid = "+lid;
        }
//        sql = sql+")";
        return sql;
    }

}
