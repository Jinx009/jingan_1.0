package com.protops.gateway.util;

import com.protops.gateway.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouzhihao on 2014/12/1.
 */
public class StatsUtil {


    public static Integer determineCnt(String curDate, Object[] resultSet) {

        String pvDate = (String) resultSet[1];
        Integer cnt = 0;
        if (curDate.equals(pvDate)) {
            cnt = ((Long) resultSet[0]).intValue();
        } else {
            cnt = 0;
        }
        return cnt;
    }

    public static Integer bingoDate(String startDate, List<Object[]> list) {
        Integer cnt = 0;
        for (int i = 0; i < list.size(); i++) {
            cnt = determineCnt(startDate, list.get(i));
            if (cnt != 0) {
                return cnt;
            }
        }
        return cnt;
    }

    public static Date nextDay(Date startDate) {
        return DateUtil.getAddedTime(startDate, Calendar.DAY_OF_WEEK, 1);
    }

    public static Date nextWeek(Date startDate) {
        return DateUtil.getAddedTime(startDate, Calendar.WEEK_OF_YEAR, 1);
    }

    public static Date nextMonth(Date startDate) {
        return DateUtil.getAddedTime(startDate, Calendar.MONTH, 1);
    }

    public static Date getLastWeek(Date date){
        return DateUtil.getAddedTime(date, Calendar.DAY_OF_WEEK, -7);
    }

    public static Date getLastThreeMonth(Date date){
        return DateUtil.getAddedTime(date, Calendar.MONTH, -3);
    }

    public static Date getLastTwelveMonth(Date date){
        return DateUtil.getAddedTime(date, Calendar.MONTH, -12);
    }
    public static Date getLastThirtyDays(Date date){
        return DateUtil.getAddedTime(date, Calendar.DAY_OF_WEEK, -30);
    }
    public static Date getLastOneHundredDays(Date date){
        return DateUtil.getAddedTime(date, Calendar.DAY_OF_WEEK, -100);
    }

}
