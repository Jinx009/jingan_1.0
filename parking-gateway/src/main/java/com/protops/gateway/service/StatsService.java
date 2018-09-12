package com.protops.gateway.service;

import com.protops.gateway.dao.StatsResultDao;
import com.protops.gateway.dao.StatsSampleDao;
import com.protops.gateway.domain.StatsResult;
import com.protops.gateway.domain.StatsSample;
import com.protops.gateway.domain.iot.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damen on 2016/4/16.
 */
@Service
@Transactional
public class StatsService {

    @Autowired
    StatsResultDao statsResultDao;

    @Autowired
    StatsSampleDao statsSampleDao;

    public List<StatsResult> getByHourByLocationAll(List<Area> areas, String endDate){

        List<Integer> ids = new ArrayList<Integer>();
        for(Area area : areas){
            ids.add(area.getId());
        }

        return statsResultDao.getByHourByLocationAll(ids, endDate);
    }

    public List<StatsResult> getByDayByLocationAll(List<Area> areas, String startStr, String dateStr) {

        List<Integer> ids = new ArrayList<Integer>();
        for(Area area : areas){
            ids.add(area.getId());
        }

        return statsResultDao.getByDayByLocationAll(ids, startStr, dateStr);
    }

    public List<StatsResult> getMonthByLocationAll(List<Area> areas, String startStr, String dateStr) {

        List<Integer> ids = new ArrayList<Integer>();
        for(Area area : areas){
            ids.add(area.getId());
        }

        return statsResultDao.getByMonthByLocationAll(ids, startStr, dateStr);
    }

    public List<StatsResult> getRushHourByLocationAll(List<Area> areas, String startStr, String dateStr) {

        List<Integer> ids = new ArrayList<Integer>();
        for(Area area : areas){
            ids.add(area.getId());
        }

        return statsResultDao.getRushHourByLocationAll(ids, startStr, dateStr);
    }

    public List<StatsResult> getByHourAll(Integer areaId, String endDate){
        return statsResultDao.getByHourAll(areaId, endDate);
    }

    public List<StatsResult> getByDayAll(Integer areaId, String startStr, String dateStr) {
        return statsResultDao.getByDayAll(areaId, startStr, dateStr);
    }

    public List<StatsResult> getMonthAll(Integer areaId, String startStr, String dateStr) {
        return statsResultDao.getByMonthAll(areaId, startStr, dateStr);
    }

    public List<StatsResult> getRushHourAll(Integer areaId, String startStr, String dateStr) {
        return statsResultDao.getRushHourAll(areaId, startStr, dateStr);
    }

    /**
     *
     * @param areas
     * @param endDate
     * @return
     */

    public List<StatsResult> getByHourByLocation(List<Area> areas, String endDate){

        List<Integer> ids = new ArrayList<Integer>();
        for(Area area : areas){
            ids.add(area.getId());
        }

        return statsResultDao.getByHourByLocation(ids, endDate);
    }

    public List<StatsResult> getByDayByLocation(List<Area> areas, String startStr, String dateStr) {

        List<Integer> ids = new ArrayList<Integer>();
        for(Area area : areas){
            ids.add(area.getId());
        }

        return statsResultDao.getByDayByLocation(ids, startStr, dateStr);
    }

    public List<StatsResult> getMonthByLocation(List<Area> areas, String startStr, String dateStr) {

        List<Integer> ids = new ArrayList<Integer>();
        for(Area area : areas){
            ids.add(area.getId());
        }

        return statsResultDao.getByMonthByLocation(ids, startStr, dateStr);
    }

    public List<StatsResult> getRushHourByLocation(List<Area> areas, String startStr, String dateStr) {

        List<Integer> ids = new ArrayList<Integer>();
        for(Area area : areas){
            ids.add(area.getId());
        }

        return statsResultDao.getRushHourByLocation(ids, startStr, dateStr);
    }

    public List<StatsResult> getByHour(Integer areaId, String endDate){
        return statsResultDao.getByHour(areaId, endDate);
    }

    public List<StatsResult> getByDay(Integer areaId, String startStr, String dateStr) {
        return statsResultDao.getByDay(areaId, startStr, dateStr);
    }

    public List<StatsResult> getMonth(Integer areaId, String startStr, String dateStr) {
        return statsResultDao.getByMonth(areaId, startStr, dateStr);
    }

    public List<StatsResult> getRushHour(Integer areaId, String startStr, String dateStr) {
        return statsResultDao.getRushHour(areaId, startStr, dateStr);
    }

    public void logSample(StatsSample statsSample) {
        statsSampleDao.save(statsSample);
    }
}
