package com.protops.gateway.dao;

import com.protops.gateway.domain.StatsResult;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by damen on 2016/4/16.
 */
@Repository
public class StatsResultDao extends HibernateBaseDao<StatsResult, Integer> {


    public List<StatsResult> getByHourByLocationAll(List<Integer> areaList, String endDate) {

        String sql = "select round(avg(available_cnt),0) as availableCnt, round(avg(total_cnt),0) as totalCnt, round(avg(percent),0) as percent, date_format(log_time, '%H') as unit from tbl_stats_sample where date_format(log_time, '%H') between 00 and 23 and date_format(log_time, '%Y-%m-%d') = ? and area_id in (:areaList) group by date_format(log_time, '%Y-%m-%d %H');";

        return findBySqlWithListAndParams(sql, StatsResult.class, "areaList", areaList, endDate);
    }

    public List<StatsResult> getByDayByLocationAll(List<Integer> areaList, String startStr, String dateStr) {

        String sql = "select round(avg(available_cnt),0) as availableCnt, round(avg(total_cnt),0) as totalCnt, round(avg(percent),0) as percent, date_format(log_time, '%Y-%m-%d') as unit from tbl_stats_sample where date_format(log_time, '%H') between 00 and 23 and date_format(log_time, '%Y-%m-%d') between ?  and ? and area_id in (:areaList) group by date_format(log_time, '%Y-%m-%d');";

        return findBySqlWithListAndParams(sql, StatsResult.class, "areaList", areaList, startStr, dateStr);

    }


    public List<StatsResult> getByMonthByLocationAll(List<Integer> areaList, String startStr, String dateStr) {
        String sql = "select round(avg(available_cnt),0) as availableCnt, round(avg(total_cnt),0) as totalCnt, round(avg(percent),0) as percent, date_format(log_time, '%Y-%m') as unit from tbl_stats_sample where date_format(log_time, '%H') between 00 and 23 and date_format(log_time, '%Y-%m') between ? and ? and area_id in (:areaList) group by unit";
        return findBySqlWithListAndParams(sql, StatsResult.class, "areaList", areaList, startStr, dateStr);
    }

    public List<StatsResult> getRushHourByLocationAll(List<Integer> areaList, String startStr, String dateStr) {
        String sql = "select round(avg(percent),0) as percent, date_format(log_time, '%H') as unit, `week` from tbl_stats_sample where date_format(log_time, '%Y-%m-%d') between ? and ?  and area_id in (:areaList) group by date_format(log_time, '%H'), `week`";
        return findBySqlWithListAndParams(sql, StatsResult.class, "areaList", areaList, startStr, dateStr);
    }


    public List<StatsResult> getByHourAll(Integer areaId, String endDate) {

        String sql = "select round(avg(available_cnt),0) as availableCnt, round(avg(total_cnt),0) as totalCnt, round(avg(percent),0) as percent, date_format(log_time, '%H') as unit from tbl_stats_sample where date_format(log_time, '%H') between 00 and 23 and  date_format(log_time, '%Y-%m-%d') = ? and area_id = ? group by date_format(log_time, '%Y-%m-%d %H');";

        return findBySql(sql, StatsResult.class, endDate, areaId);
    }

    public List<StatsResult> getByDayAll(Integer areaId, String startStr, String dateStr) {

        String sql = "select round(avg(available_cnt),0) as availableCnt, round(avg(total_cnt),0) as totalCnt, round(avg(percent),0) as percent, date_format(log_time, '%Y-%m-%d') as unit from tbl_stats_sample where date_format(log_time, '%H') between 00 and 23 and date_format(log_time, '%Y-%m-%d') between ?  and ? and area_id = ? group by date_format(log_time, '%Y-%m-%d');";

        return findBySql(sql, StatsResult.class, startStr, dateStr, areaId);

    }


    public List<StatsResult> getByMonthAll(Integer areaId, String startStr, String dateStr) {
        String sql = "select round(avg(available_cnt),0) as availableCnt, round(avg(total_cnt),0) as totalCnt, round(avg(percent),0) as percent, date_format(log_time, '%Y-%m') as unit from tbl_stats_sample where date_format(log_time, '%H') between 00 and 23 and date_format(log_time, '%Y-%m') between ? and ? and area_id = ? group by unit";
        return findBySql(sql, StatsResult.class, startStr, dateStr, areaId);
    }

    public List<StatsResult> getRushHourAll(Integer areaId, String startStr, String dateStr) {
        String sql = "select round(avg(percent),0) as percent, date_format(log_time, '%H') as unit, `week` from tbl_stats_sample where date_format(log_time, '%Y-%m-%d') between ? and ?  and area_id = ? group by date_format(log_time, '%H'), `week`";
        return findBySql(sql, StatsResult.class, startStr, dateStr, areaId);
    }
    /**
     *
     * @param areaList
     * @param endDate
     * @return
     */
    public List<StatsResult> getByHourByLocation(List<Integer> areaList, String endDate) {

        String sql = "select round(avg(available_cnt),0) as availableCnt, round(avg(total_cnt),0) as totalCnt, round(avg(percent),0) as percent, date_format(log_time, '%H') as unit from tbl_stats_sample where date_format(log_time, '%H') between 06 and 22 and date_format(log_time, '%Y-%m-%d') = ? and area_id in (:areaList) group by date_format(log_time, '%Y-%m-%d %H');";

        return findBySqlWithListAndParams(sql, StatsResult.class, "areaList", areaList, endDate);
    }

    public List<StatsResult> getByDayByLocation(List<Integer> areaList, String startStr, String dateStr) {

        String sql = "select round(avg(available_cnt),0) as availableCnt, round(avg(total_cnt),0) as totalCnt, round(avg(percent),0) as percent, date_format(log_time, '%Y-%m-%d') as unit from tbl_stats_sample where date_format(log_time, '%H') between 06 and 22 and date_format(log_time, '%Y-%m-%d') between ?  and ? and area_id in (:areaList) group by date_format(log_time, '%Y-%m-%d');";

        return findBySqlWithListAndParams(sql, StatsResult.class, "areaList", areaList, startStr, dateStr);

    }


    public List<StatsResult> getByMonthByLocation(List<Integer> areaList, String startStr, String dateStr) {
        String sql = "select round(avg(available_cnt),0) as availableCnt, round(avg(total_cnt),0) as totalCnt, round(avg(percent),0) as percent, date_format(log_time, '%Y-%m') as unit from tbl_stats_sample where date_format(log_time, '%H') between 06 and 22 and date_format(log_time, '%Y-%m') between ? and ? and area_id in (:areaList) group by unit";
        return findBySqlWithListAndParams(sql, StatsResult.class, "areaList", areaList, startStr, dateStr);
    }

    public List<StatsResult> getRushHourByLocation(List<Integer> areaList, String startStr, String dateStr) {
        String sql = "select round(avg(percent),0) as percent, date_format(log_time, '%H') as unit, `week` from tbl_stats_sample where date_format(log_time, '%Y-%m-%d') between ? and ?  and area_id in (:areaList) group by date_format(log_time, '%H'), `week`";
        return findBySqlWithListAndParams(sql, StatsResult.class, "areaList", areaList, startStr, dateStr);
    }


    public List<StatsResult> getByHour(Integer areaId, String endDate) {

        String sql = "select round(avg(available_cnt),0) as availableCnt, round(avg(total_cnt),0) as totalCnt, round(avg(percent),0) as percent, date_format(log_time, '%H') as unit from tbl_stats_sample where date_format(log_time, '%H') between 06 and 22 and  date_format(log_time, '%Y-%m-%d') = ? and area_id = ? group by date_format(log_time, '%Y-%m-%d %H');";

        return findBySql(sql, StatsResult.class, endDate, areaId);
    }

    public List<StatsResult> getByDay(Integer areaId, String startStr, String dateStr) {

        String sql = "select round(avg(available_cnt),0) as availableCnt, round(avg(total_cnt),0) as totalCnt, round(avg(percent),0) as percent, date_format(log_time, '%Y-%m-%d') as unit from tbl_stats_sample where date_format(log_time, '%H') between 06 and 22 and date_format(log_time, '%Y-%m-%d') between ?  and ? and area_id = ? group by date_format(log_time, '%Y-%m-%d');";

        return findBySql(sql, StatsResult.class, startStr, dateStr, areaId);

    }


    public List<StatsResult> getByMonth(Integer areaId, String startStr, String dateStr) {
        String sql = "select round(avg(available_cnt),0) as availableCnt, round(avg(total_cnt),0) as totalCnt, round(avg(percent),0) as percent, date_format(log_time, '%Y-%m') as unit from tbl_stats_sample where date_format(log_time, '%H') between 06 and 22 and date_format(log_time, '%Y-%m') between ? and ? and area_id = ? group by unit";
        return findBySql(sql, StatsResult.class, startStr, dateStr, areaId);
    }

    public List<StatsResult> getRushHour(Integer areaId, String startStr, String dateStr) {
        String sql = "select round(avg(percent),0) as percent, date_format(log_time, '%H') as unit, `week` from tbl_stats_sample where date_format(log_time, '%Y-%m-%d') between ? and ?  and area_id = ? group by date_format(log_time, '%H'), `week`";
        return findBySql(sql, StatsResult.class, startStr, dateStr, areaId);
    }
}
