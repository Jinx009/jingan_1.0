package com.protops.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.protops.gateway.constants.Constants;
import com.protops.gateway.domain.iot.Router;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.domain.log.SensorDeviceLog;
import com.protops.gateway.service.RouterService;
import com.protops.gateway.service.SensorService;
import com.protops.gateway.service.log.SensorDeviceLogService;
import com.protops.gateway.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@Controller
public class IoTDataController {

    private static final Logger log = LoggerFactory.getLogger(IoTDataController.class);

    @Autowired
    private SensorService sensorService;
    @Autowired
    private RouterService routerService;
    @Autowired
    private SensorDeviceLogService sensorDeviceLogService;

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

    @RequestMapping(value = "/sensor/heartBeatLog/report", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String heartBeatLog(@RequestBody byte[] bytes){
        try {
            SensorDeviceLog sensorDeviceLog = JSONObject.parseObject(new String(bytes, Constants.Default_SysEncoding),SensorDeviceLog.class);
            if(sensorDeviceLog!=null){
                sensorDeviceLog.setCreateTime(new Date());
                sensorDeviceLogService.save(sensorDeviceLog);
            }
        }catch (Exception e){
            log.error("error:{}",e);
        }

        return "{\"status\":\"ok\"}";
    }

}
