package com.protops.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.protops.gateway.constants.Constants;
import com.protops.gateway.dao.log.SensorOperationLogDao;
import com.protops.gateway.domain.iot.Router;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.domain.log.SensorDeviceLog;
import com.protops.gateway.domain.log.SensorOperationLog;
import com.protops.gateway.service.RouterService;
import com.protops.gateway.service.SensorService;
import com.protops.gateway.service.log.SensorDeviceLogService;
import com.protops.gateway.util.MD5Utils;
import com.protops.gateway.utils.baoxin.SendUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IoTDataController {

    private static final Logger log = LoggerFactory.getLogger(IoTDataController.class);

    @Autowired
    private SensorService sensorService;
    @Autowired
    private RouterService routerService;
    @Autowired
    private SensorDeviceLogService sensorDeviceLogService;
    @Autowired
    private SensorOperationLogDao sensorOperationLogDao;

    /**
     * 摄像头数据输入
     * @param bytes
     * @return
     */
    @RequestMapping(value = "/iot/sensor/vedioReport", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String vedioSend(@RequestBody byte[] bytes){
        try {
            JSONObject json = JSONObject.parseObject(new String(bytes, Constants.Default_SysEncoding));
            String mac = json.getString("mac");
            String cameraTime =  json.getString("ChangeTime");
            String cameraId = json.getString("cameraId");
            String cph = json.getString("cph");
            String cpColor = json.getString("cpColor");
            String status = json.getString("status");
            String picLink = json.getString("picLink");
            Sensor sensor = sensorService.getByMac(mac);
            sensor.setPicLink(picLink);
            sensor.setCameraName(cameraId);
            sensor.setVedioStatus(status);
            sensor.setCameraId(cameraId);
            sensor.setCpColor(cpColor);
            sensor.setVedioTime(cameraTime);
            boolean res = false;
            SensorOperationLog log = new SensorOperationLog();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            log.setMac(sensor.getMac());
            log.setChangeTime(sdf.parse(cameraTime));
            log.setAvailable(Integer.valueOf(status));
            log.setFailTimes(0);
            log.setSendStatus(0);
            log.setDescription("");
            log.setAreaId(sensor.getAreaId());
            log.setCreateTime(new Date());
            log.setCameraId(cameraId);
            log.setCpColor(cpColor);
            log.setCph(cph);
            log.setPicLink(picLink);
            log.setType(1);
            log.setStatus(status);
            sensorOperationLogDao.save(log);
            if(status.equals(String.valueOf(sensor.getAvailable()))||"2".equals(status)){
                Date last = sensor.getLastSeenTime();
                Date now = sdf.parse(sensor.getVedioTime());
                int c = (int)((now.getTime() - last.getTime()) / 1000);
                if(-120<c&&c<120) {
                    res = SendUtils.send(sensor.getLastSeenTime(), sensor.getMac(), String.valueOf(sensor.getAvailable()),
                            "", sensor.getSensorTime(), sensor.getVedioTime(), sensor.getCameraId(),
                            sensor.getCph(), sensor.getCpColor(), sensor.getVedioStatus(), sensor.getPicLink());
                }
            }else{
                sensor.setSensorTime("");
                sensor.setLastSeenTime(sdf.parse(cameraTime));
                res = SendUtils.send(sensor.getLastSeenTime(),sensor.getMac(),"",
                        "",sensor.getSensorTime(),sensor.getVedioTime(),sensor.getCameraId(),
                        sensor.getCph(),sensor.getCpColor(),sensor.getVedioStatus(),sensor.getPicLink());
            }
            log = sensorOperationLogDao.get(log.getId());
            if(res){
                log.setSendStatus(1);
                log.setSendTime(new Date());
                sensorOperationLogDao.update(log);
            }else{
                log = sensorOperationLogDao.get(log.getId());
                log.setFailTimes(log.getFailTimes() + 1);
                sensorOperationLogDao.update(log);
            }
            sensorService.update(sensor);
        }catch (Exception e){
            log.error("error:{}",e);
        }
        return "{\"status\":\"ok\"}";
    }


    @RequestMapping(value = "/iot/sensor/report/voltage", method = RequestMethod.POST)
    @ResponseBody
    public String process(
            @RequestParam(value = "m") String sensorMac,
            @RequestParam(value = "u") String voltage,
            @RequestParam(value = "s") String secret) throws IOException {

        try {
            Sensor sensor = sensorService.getByMac(sensorMac);
            Router router = routerService.getByMac(sensor.getRouterMac());
            String p = "m="+sensorMac+"&s="+router.getSecret()+"&u="+voltage;
            String sign = MD5Utils.md5(p);
            if(sign.equals(secret)){
                sensor.setUpdateTime(new Date());
                sensor.setUid(voltage);
                sensorService.update(sensor);
            }
        }catch (Exception e){
            log.error("error:{}",e);
        }

        return "ok";
    }

    @RequestMapping(value = "/openApi/changeSendStatus")
    @ResponseBody
    public Map<String,String> changeStatus(@RequestParam(value = "status", required = true) String status,
                                           @RequestParam(value = "sign", required = true) String sign,
                                           @RequestParam(value = "mac", required = true) String mac){
        Map<String,String> map = new HashMap<String, String>();
        map.put("code","500");
        map.put("msg","Sign not invalid");
        try {
            if(MD5Utils.md5("mac="+mac+"&status="+status+"&sign=zhanway_guozhi").equals(sign)){
                Sensor sensor = sensorService.getByMac(mac);
                sensor.setAreaId(1);
                if("0".equals(status)){
                    sensor.setAreaId(2);
                }
                sensorService.update(sensor);
                map.put("code","200");
                map.put("msg","ok");
            }
        }catch (Exception e){
            log.error("error:{}",e);
        }
        return map;
    }


//    @RequestMapping(value = "/sensor/heartBeatLog/report", method = RequestMethod.POST, produces = "application/json")
//    @ResponseBody
//    public String heartBeatLog(@RequestBody byte[] bytes){
//        try {
//            SensorDeviceLog sensorDeviceLog = JSONObject.parseObject(new String(bytes, Constants.Default_SysEncoding),SensorDeviceLog.class);
//            if(sensorDeviceLog!=null){
//                sensorDeviceLog.setCreateTime(new Date());
//                Sensor sensor = sensorService.getByMac(sensorDeviceLog.getMac());
//                sensor.setAddr(sensorDeviceLog.getRssi());
//                sensorService.update(sensor);
//                sensorDeviceLogService.save(sensorDeviceLog);
//                FileWriter fileWriter = new FileWriter("/apps/logs/heart_log_time.txt", false);
//                fileWriter.write(String.valueOf(new Date().getTime()));
//                fileWriter.flush();
//                fileWriter.close();
//            }
//        }catch (Exception e){
//            log.error("error:{}",e);
//        }
//
//        return "{\"status\":\"ok\"}";
//    }


    @RequestMapping(value = "/sensor/heartBeatLog/report", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String heartBeatLog(@RequestBody byte[] bytes){
        try {
            SensorDeviceLog sensorDeviceLog = JSONObject.parseObject(new String(bytes, Constants.Default_SysEncoding),SensorDeviceLog.class);
            if(sensorDeviceLog!=null){
                sensorDeviceLog.setCreateTime(new Date());
                Sensor sensor = sensorService.getByMac(sensorDeviceLog.getMac());
                sensor.setAddr(sensorDeviceLog.getRssi());
                sensorService.update(sensor);
                sensorDeviceLogService.save(sensorDeviceLog);
                FileWriter fileWriter = new FileWriter("/home/baoadmin/logs/heart_log_time.txt", false);
                fileWriter.write(String.valueOf(new Date().getTime()));
                fileWriter.flush();
                fileWriter.close();
            }
        }catch (Exception e){
            log.error("error:{}",e);
        }

        return "{\"status\":\"ok\"}";
    }

}
