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

import java.io.FileWriter;
import java.io.IOException;
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
                FileWriter fileWriter = new FileWriter("/apps/logs/heart_log_time.txt", false);
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
