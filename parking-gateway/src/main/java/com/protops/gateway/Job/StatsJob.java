package com.protops.gateway.Job;

import com.protops.gateway.domain.AreaVo;
import com.protops.gateway.domain.StatsSample;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.service.AreaService;
import com.protops.gateway.service.LocationService;
import com.protops.gateway.service.SensorService;
import com.protops.gateway.service.StatsService;
import com.protops.gateway.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by damen on 2016/4/17.
 */
public class StatsJob {

    private static final Logger logger = LoggerFactory.getLogger(StatsJob.class);

    private List<Integer> statsIds;

    @Autowired
    StatsService statsService;

    @Autowired
    LocationService locationService;

    @Autowired
    AreaService areaService;

    @Autowired
    SensorService sensorService;

    @Autowired
    AreaJob areaJob;

    /**
     * 搜索所有Location是leaf的，找到每一个area，都进行采样
     */
    public static void main(String[] args){

        Date d = DateUtil.parseDate("2016-04-18", DateUtil.DATE_FMT_DISPLAY2);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        System.out.println(c.get(Calendar.DAY_OF_WEEK));

    }

    public void process() {

        try {

            Date logDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(logDate);
            Integer year = calendar.get(Calendar.YEAR);
            Integer month = calendar.get(Calendar.MONTH) + 1;
            Integer day = calendar.get(Calendar.DAY_OF_MONTH);
            Integer week = calendar.get(Calendar.DAY_OF_WEEK);
            Integer hour = calendar.get(Calendar.HOUR_OF_DAY);

            logger.warn("job stats start {}", DateUtil.getDate(logDate, DateUtil.DATE_FMT_DISPLAY));

            MathContext mc = new MathContext(10, RoundingMode.HALF_UP);

            List<Location> locationList = locationService.getByIds(getStatsIds());

            for (Location location : locationList) {

                List<Area> areaList = areaService.getAreasByLocationId(location.getId());

                if (areaList == null || areaList.size() == 0) {
                    continue;
                }

                List<AreaVo> areaCntList = sensorService.getByAreaId(areaList);

                for (AreaVo areaVo : areaCntList) {

                    StatsSample statsSample = new StatsSample();

                    statsSample.setAreaId(areaVo.getId());

                    statsSample.setAvailableCnt(areaVo.getAvailableCnt().intValue());

                    BigInteger totalCnt = areaJob.getCnt(areaVo.getId());

                    statsSample.setTotalCnt(totalCnt.intValue());

                    Integer percent = calcPercent(statsSample, mc);
                    statsSample.setPercent(percent);

                    statsSample.setYear(year);
                    statsSample.setMonth(month);
                    statsSample.setWeek(week);
                    statsSample.setDay(day);
                    statsSample.setHour(hour);
                    statsSample.setLogTime(logDate);

                    statsService.logSample(statsSample);

                }

                boolean in = false;

                for (Area area : areaList) {

                    // 遍历每个areaVo，如果找到就设置为true，并且break掉。如果没有找到，就是false，是false，就是满了
                    for (AreaVo areaVo : areaCntList) {

                        if (areaVo.getId() == area.getId()) {
                            in = true;
                            break;
                        }

                    }

                    if (!in) {
                        StatsSample statsSample = new StatsSample();

                        statsSample.setAreaId(area.getId());

                        BigInteger totalCnt = areaJob.getCnt(area.getId());

                        statsSample.setAvailableCnt(0);
                        statsSample.setTotalCnt(totalCnt.intValue());

                        Integer percent = calcPercent(statsSample, mc);
                        statsSample.setPercent(percent);

                        statsSample.setYear(year);
                        statsSample.setMonth(month);
                        statsSample.setWeek(week);
                        statsSample.setDay(day);
                        statsSample.setHour(hour);
                        statsSample.setLogTime(logDate);

                        statsService.logSample(statsSample);
                    }

                    in = false;

                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private Integer calcPercent(StatsSample statsSample, MathContext mc) {

        Integer availability = statsSample.getTotalCnt() - statsSample.getAvailableCnt();

        BigDecimal totalCntDecimal = new BigDecimal(statsSample.getTotalCnt() + "");
        BigDecimal availableDecimal = new BigDecimal(availability + "");

        BigDecimal ret = availableDecimal.divide(totalCntDecimal, mc).multiply(new BigDecimal(100 + ""));

        return ret.intValue();
    }


    public List<Integer> getStatsIds() {
        return statsIds;
    }

    public void setStatsIds(List<Integer> statsIds) {
        this.statsIds = statsIds;
    }
}
