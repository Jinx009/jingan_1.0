package com.protops.gateway.service.device;

import com.protops.gateway.dao.log.SensorInOutLogDao;
import com.protops.gateway.domain.log.SensorInOutLog;
import com.protops.gateway.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jinx on 4/13/17.
 */
@Service
public class GeCarInOutService {

    @Autowired
    private SensorInOutLogDao sensorInOutLogDao;

    public List<SensorInOutLog> getByArea(String dateStr,Integer areaId){
        return sensorInOutLogDao.getByAreaId(areaId,dateStr);
    }

    public List<SensorInOutLog> getByAreaMonth(String dateStr,Integer areaId){
        Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);
        Date startDate = DateUtil.getAddedTime(endDate, Calendar.MONTH, -1);
        String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);
        return sensorInOutLogDao.getByAreaIdMonth(areaId,dateStr,startStr);
    }

    public List<SensorInOutLog> getByAreaSimpleMonth(String dateStr,Integer areaId){
        return sensorInOutLogDao.getByAreaIdSimpleMonth(areaId,dateStr);
    }

}
