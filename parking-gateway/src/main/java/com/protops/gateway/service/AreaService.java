package com.protops.gateway.service;

import com.protops.gateway.dao.AreaDao;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.vo.AreaCnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by damen on 2016/4/9.
 */
@Service
@Transactional
public class AreaService {

    @Autowired
    AreaDao areaDao;

    public Area get(Integer id) {
        return areaDao.get(id);
    }

    public List<Area> getAreasByLocationId(Integer locationId) {
        return areaDao.getAreasByLocationId(locationId);
    }

    public List<AreaCnt> getTotalCntByArea() {

        return null;
    }

    public List<Area> getBaseAreasByLocationId(Integer id){
        return areaDao.getBaseAreasByLocationId(id);
    }

    public void update(Area area){
        areaDao.update(area);
    }

    public List<Area> getByDistrict(String district) {
        return areaDao.getByDistrict(district);
    }
}
