package com.protops.gateway.service;

import com.protops.gateway.dao.LocationDao;
import com.protops.gateway.domain.iot.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by damen on 2016/4/9.
 */
@Service
@Transactional
public class LocationService {

    @Autowired
    LocationDao locationDao;


    public List<Location> findNeedNotice(){
        return locationDao.findNeedNotice();
    }

    public List<Location> findNeedDeviceNotice(){
        return locationDao.findNeedDeviceNotice();
    }

    public void update(Location location){
        locationDao.update(location);
    }


    public Location get(Integer locationId) {
        return locationDao.get(locationId);
    }

    public List<Location> findAllLeafLocation() {

        return locationDao.findLocationByLevel(2);

    }

    public List<Location> findByAppInfoId(Integer appInfoId) {
        return locationDao.findOpenByAppInfoId(appInfoId);
    }

    public List<Location> findOpenByAppInfoId(Integer appInfoId) {
        return locationDao.findOpenByAppInfoId(appInfoId);
    }
    public List<Location> list() {
        return locationDao.getAll();
    }

    public List<Location> getByTag(String district) {
        return locationDao.getByTag(district);
    }

    public List<Location> getByIds(List<Integer> ids) {

        return locationDao.getByIds(ids);

    }
}
