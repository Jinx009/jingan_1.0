package com.protops.gateway.Job.notice;

import com.protops.gateway.dao.log.SensorOperationLogService;
import com.protops.gateway.domain.AppInfo;
import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.domain.log.SensorDeviceLog;
import com.protops.gateway.domain.log.SensorOperationLog;
import com.protops.gateway.service.*;
import com.protops.gateway.service.log.SensorDeviceLogService;
import com.protops.gateway.utils.baoxin.SendUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jinx on 4/19/17.
 */
@Component
@Lazy(value = false)
public class SensorNoticeTask {

    private static final Logger logger = LoggerFactory.getLogger(SensorNoticeTask.class);

    @Autowired
    private SensorService sensorService;
    @Autowired
    private SensorDeviceLogService sensorDeviceLogService;



//    /**
//     * 静安宝信推送心跳
//     */
//    @Scheduled(cron = "0 0 16 * * ?")//每天中午16点推送一次
//    public void sendJinganDeviceHeart() {
//        try { logger.error("jingan device[start]");
//           List<Sensor> list = sensorService.getSensorsByArea(1);
//           List<Sensor> arrays = new ArrayList<Sensor>();
//           if(list!=null&&!list.isEmpty()){
//               for(Sensor sensor:list){
//                   Map<String,Object> map = sensorDeviceLogService.findByMacAndDate(sensor.getMac());
//                   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                   sensor.setBatteryVoltage(String.valueOf(map.get("a")));
//                   if(null!=map.get("b")){
//                       sensor.setLastSeenTime(sdf.parse(String.valueOf(map.get("b"))));
//                   }else{
//                       sensor.setLastSeenTime(null);
//                   }
//                   arrays.add(sensor);
//               }
//               SendUtils.sendDeviceHeart(arrays);
//           }
//        }catch(Exception e){
//            logger.error("jingan device[error:{}]", e);
//        }
//    }


//    /**
//     * 设备业务状态上报
//     */
//    @Scheduled(fixedRate = 40 * 1000)//每10S执行一次
//    public void sendNormal() {
//        List<Location> locations = locationService.findNeedNotice();
//        if (locations != null && !locations.isEmpty()) {
//            for (Location location : locations) {
//                AppInfo appInfo = appInfoService.findById(location.getAppInfoId());
//                List<SensorOperationLog> sensorOperationLogs = sensorOperationLogService.getLocationId(location.getId());
//               if ("baoxin_1".equals(appInfo.getAppId())) {//宝信第一个项目
//                    if (SendUtils.send(sensorOperationLogs)) {
//                        for (SensorOperationLog sensorOperationLog : sensorOperationLogs) {
//                            sensorOperationLog = sensorOperationLogService.find(sensorOperationLog.getId());
//                            sensorOperationLog.setSendStatus(1);
//                            sensorOperationLog.setSendTime(new Date());
//                            sensorOperationLogService.update(sensorOperationLog);
//                        }
//                    } else {
//                        for (SensorOperationLog sensorOperationLog : sensorOperationLogs) {
//                            sensorOperationLog = sensorOperationLogService.find(sensorOperationLog.getId());
//                            sensorOperationLog.setFailTimes(sensorOperationLog.getFailTimes() + 1);
//                            sensorOperationLogService.update(sensorOperationLog);
//                        }
//                    }
//                }
//            }
//        }
//    }



}
