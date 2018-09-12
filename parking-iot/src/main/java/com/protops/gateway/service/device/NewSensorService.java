package com.protops.gateway.service.device;

import com.protops.gateway.dao.log.SensorDeviceLogDao;
import com.protops.gateway.dao.log.SensorInOutLogDao;
import com.protops.gateway.dao.log.SensorOperationLogDao;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.domain.log.SensorDeviceLog;
import com.protops.gateway.domain.log.SensorInOutLog;
import com.protops.gateway.domain.log.SensorOperationLog;
import com.protops.gateway.util.StringUtils;
import com.protops.gateway.utils.baoxin.SendUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by jinx on 3/24/17.
 */
@Service
@Transactional
public class NewSensorService {

    private static final Logger log = LoggerFactory.getLogger(NewSensorService.class);

    @Autowired
    private SensorDeviceLogDao sensorDeviceLogDao;
    @Autowired
    private SensorOperationLogDao sensorOperationLogDao;
    @Autowired
    private SensorInOutLogDao sensorInOutLogDao;




    public void saveDeviceLog(Sensor sensor) {
        SensorDeviceLog sensorDeviceLog = new SensorDeviceLog();
        sensorDeviceLog.setMac(sensor.getMac());
        sensorDeviceLog.setCreateTime(sensor.getHappenTime());
        sensorDeviceLog.setUid(sensor.getUid());
        sensorDeviceLog.setFirmwareVersion(sensor.getFirmwareVersion());
        sensorDeviceLog.setBatteryVoltage(sensor.getBatteryVoltage());
        sensorDeviceLog.setXMag(sensor.getXMag());
        sensorDeviceLog.setYMag(sensor.getYMag());
        sensorDeviceLog.setZMag(sensor.getZMag());
        sensorDeviceLog.setBaseEnergy(sensor.getBaseEnergy());
        sensorDeviceLog.setHeartBeatInterval(sensor.getHeartBeatInterval());
        sensorDeviceLog.setChannelId(sensor.getChannelId());
        sensorDeviceLog.setPanId(sensor.getPanId());
        sensorDeviceLog.setFrequency(sensor.getFrequency());
        sensorDeviceLog.setParentMac(sensor.getParentMac());
        sensorDeviceLog.setRouterMac(sensor.getRouterMac());
        sensorDeviceLog.setConnected(sensor.getConnected());
        sensorDeviceLog.setType(sensor.getType());
        sensorDeviceLog.setMode(sensor.getMode());
        sensorDeviceLog.setHardwareVersion(sensor.getHardwareVersion());
        sensorDeviceLog.setPaid(sensor.getPaid());
        sensorDeviceLogDao.save(sensorDeviceLog);
    }

    public void saveOperationLog(Sensor sensor) {
        SensorOperationLog sensorOperationLog = new SensorOperationLog();
//        SensorOperationLog sensorOperationLog2 = sensorOperationLogDao.findByLogIdAndStatus(sensor);
        sensorOperationLog.setAvailable(sensor.getAvailable());
        sensorOperationLog.setCreateTime(new Date());
        sensorOperationLog.setChangeTime(new Date());
        sensorOperationLog.setAreaId(sensor.getAreaId());
        sensorOperationLog.setMac(sensor.getMac());
        sensorOperationLog.setDescription(sensor.getDesc());
        sensorOperationLog.setFailTimes(0);
        sensorOperationLog.setSendStatus(0);
        sensorOperationLog.setSendTime(new Date());
        //没有对应的logId的新记录进行插入
        if (StringUtils.isNotBlank(sensor.getLogId())) {//车进
            sensorOperationLog.setLogId(sensor.getLogId());
            sensorOperationLog.setChangeTime(sensor.getHappenTime());
        }
        sensorOperationLogDao.save(sensorOperationLog);
        sensorOperationLog = sensorOperationLogDao.get(sensorOperationLog.getId());
        //宝信
        if (sensorOperationLog.getAreaId()!=null&& 1 == sensorOperationLog.getAreaId()) {
            List<SensorOperationLog> sensorOperationLogs = new ArrayList<SensorOperationLog>();
           sensorOperationLogs.add(sensorOperationLog);
            if (SendUtils.send(sensorOperationLogs)) {
               for (SensorOperationLog sensorOperationLog1 : sensorOperationLogs) {
                    sensorOperationLog1 = sensorOperationLogDao.get(sensorOperationLog1.getId());
                    sensorOperationLog1.setSendStatus(1);
                    sensorOperationLog1.setSendTime(new Date());
                    sensorOperationLogDao.update(sensorOperationLog1);
                }
            } else {
                for (SensorOperationLog sensorOperationLog1 : sensorOperationLogs) {
                   sensorOperationLog1 = sensorOperationLogDao.get(sensorOperationLog1.getId());
                   sensorOperationLog1.setFailTimes(sensorOperationLog.getFailTimes() + 1);
                   sensorOperationLogDao.update(sensorOperationLog);
                }
            }
        }

        saveInOutLog(sensorOperationLog);

    }



    /**
     * 统计停车时间
     */
    public void saveInOutLog(SensorOperationLog sensorOperationLog) {
        SensorInOutLog sensorInOutLog = new SensorInOutLog();
        if (sensorOperationLog != null) {
            sensorInOutLog.setDescription(sensorOperationLog.getDescription());
            sensorInOutLog.setMac(sensorOperationLog.getMac());
            sensorInOutLog.setCreateTime(new Date());
            sensorInOutLog.setOutTime(sensorOperationLog.getChangeTime());
            sensorInOutLog.setAreaId(sensorOperationLog.getAreaId());
            SensorInOutLog sensorInOutLog1 = sensorInOutLogDao.findByLogId(sensorOperationLog.getLogId());
            Long timeOne1 = sensorOperationLog.getChangeTime().getTime();
            if (sensorOperationLog.getAvailable() == 0 && !StringUtils.isNotBlank(sensorOperationLog.getLogId())) {//有车离开原始方法兼容
                SensorOperationLog sensorOperationLog2 = sensorOperationLogDao.getNearOut(sensorOperationLog.getMac());
                Long timeOne = sensorOperationLog.getChangeTime().getTime();
                Long timeTwo = sensorOperationLog2.getChangeTime().getTime();//如果未存在老记录那么返回自身
                Long minute = (timeOne - timeTwo) / (1000 * 60);
                SensorOperationLog sensorOperationLog3 = sensorOperationLogDao.getNearIn(sensorOperationLog.getMac());
                if ((minute.longValue() > 1 && !StringUtils.isNotBlank(sensorOperationLog.getLogId())) && sensorOperationLog3 != null) {
                    Long timeTwo1 = sensorOperationLog3.getChangeTime().getTime();
                    Long minute1 = (timeOne1 - timeTwo1) / (1000 * 60);
                    sensorInOutLog.setInTime(sensorOperationLog3.getChangeTime());
                    sensorInOutLog.setTimes(Integer.valueOf(minute1.toString()));
                    sensorInOutLogDao.save(sensorInOutLog);
                }
            } else if (StringUtils.isNotBlank(sensorOperationLog.getLogId()) && 1 == sensorOperationLog.getAvailable() && sensorInOutLog1 == null) {//新接口
                //如果未存在同状态记录插入不存在不进行操作
                //有车进入新建logId记录，待发状态变为0
                sensorInOutLog.setLogId(sensorOperationLog.getLogId());
                sensorInOutLog.setInTime(sensorOperationLog.getChangeTime());
                sensorInOutLogDao.save(sensorInOutLog);
            } else if (StringUtils.isNotBlank(sensorOperationLog.getLogId()) && 0 == sensorOperationLog.getAvailable() && sensorInOutLog1 != null && sensorInOutLog1.getOutTime() != null) {//有车离开待发状态变为2
                Long timeTwo1 = sensorInOutLog1.getInTime().getTime();
                Long minute1 = (timeOne1 - timeTwo1) / (1000 * 60);
                Integer times = Integer.valueOf(minute1.toString());
                sensorInOutLog1.setOutTime(sensorOperationLog.getChangeTime());
                sensorInOutLog1.setTimes(times);
                sensorInOutLogDao.save(sensorInOutLog1);//update
            }
        }
    }



}
