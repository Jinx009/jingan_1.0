package com.protops.gateway.service;

import com.protops.gateway.dao.AreaVoDao;
import com.protops.gateway.dao.SensorDao;
import com.protops.gateway.domain.AreaVo;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.domain.iot.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damen on 2016/3/23.
 */
@Service
@Transactional
public class SensorService {

    @Autowired
    SensorDao sensorDao;

    @Autowired
    AreaVoDao areaVoDao;

    public Sensor getByDescAndAreaId(Integer areaId,String desc){
        return sensorDao.getByDescAndAreaId(areaId,desc);
    }

    public void update(Sensor sensor){
        sensorDao.update(sensor);
    }

    public List<Sensor> getNeedNotice(Location location){
        return sensorDao.getNeedNotice(location);
    }

    public Sensor get(Integer id){
        return sensorDao.get(id);
    }

    public Sensor getByMac(String mac) {
        return sensorDao.getByMac(mac);
    }

    public void save(Sensor sensor) {
        sensorDao.save(sensor);
    }

    public List<AreaVo> getByAreaId(List<Area> areas) {

        List<Integer> ids = new ArrayList<Integer>();
        for(Area area : areas){
            ids.add(area.getId());
        }

        return areaVoDao.getGoupByAreaId(ids);
    }

    public AreaVo getUniqueByAreaId(Integer id) {
        return areaVoDao.getUniqueByAreaId(id);
    }

    public List<Sensor> getAvailableSensors(Integer areaId) {
        return sensorDao.getSensorsByStatus(areaId, 1);
    }

    public List<Sensor> getSensorsByArea(Integer areaId) {
        return sensorDao.getSensorsByArea(areaId);
    }

    public Sensor getByParentMac(String parentMac){
        return sensorDao.getByParentMac(parentMac);
    }


}
