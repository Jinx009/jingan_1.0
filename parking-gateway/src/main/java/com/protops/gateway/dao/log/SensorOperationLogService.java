package com.protops.gateway.dao.log;

import com.protops.gateway.dao.AreaDao;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.domain.log.SensorOperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jinx on 4/19/17.
 */
@Service
public class SensorOperationLogService {

    @Autowired
    private SensorOperationLogDao sensorOperationLogDao;
    @Autowired
    private AreaDao areaDao;


    public List<SensorOperationLog> findByAreaId(Integer areaId,String dateStr){
        return sensorOperationLogDao.findByAreaId(areaId,dateStr);
    }

    public List<SensorOperationLog> findByAreaId(Integer areaId,String dateStr,String mac){
        return sensorOperationLogDao.findByAreaId(areaId,dateStr,mac);
    }

    public List<SensorOperationLog> getLocationId(Integer locationId) {
        List<Area> areas = areaDao.getAreasByLocationId(locationId);
        String ids = "";
        if(areas!=null&&!areas.isEmpty()){
            for(Area area:areas){
                ids += area.getId()+",";
            }
        }

        return sensorOperationLogDao.getByLocationId(ids.substring(0,ids.length() -1));
    }

    public void save(SensorOperationLog sensorOperationLog){
        sensorOperationLogDao.save(sensorOperationLog);
    }

    public int nearStatus(SensorOperationLog sensorOperationLog){
        return sensorOperationLogDao.nearStatus(sensorOperationLog);
    }

    public List<SensorOperationLog> getLocationIdHour(Integer locationId) {
        return sensorOperationLogDao.getByLocationIdHour(locationId);
    }

    public SensorOperationLog find(Integer id){
        return sensorOperationLogDao.get(id);
    }

    public void update(SensorOperationLog sensorOperationLog) {
        sensorOperationLogDao.update(sensorOperationLog);
    }

    public SensorOperationLog getBySameLogId(SensorOperationLog sensorOperationLog){
        if(sensorOperationLog!=null&&sensorOperationLog.getLogId()!=null){
            return sensorOperationLogDao.getBySameLogId(sensorOperationLog);
        }
        return null;
    }
}
