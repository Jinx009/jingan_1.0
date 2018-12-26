package com.protops.gateway.controller;

import com.protops.gateway.Job.notice.SensorNoticeTask;
import com.protops.gateway.dao.log.SensorOperationLogService;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.service.*;
import com.protops.gateway.service.log.SensorDeviceLogService;
import com.protops.gateway.utils.baoxin.SendUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by damen on 2016/2/14.
 */
@Controller
public class GlobalErrController {

    private static final Logger logger = LoggerFactory.getLogger(GlobalErrController.class);

    @Autowired
    private LocationService locationService;
    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private SensorOperationLogService sensorOperationLogService;
    @Autowired
    private RouterService routerService;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private SensorDeviceLogService sensorDeviceLogService;
    @Autowired
    private ErrorFlowService errorFlowService;

    @RequestMapping(value = {"value = /weixin/h5/error"})
    public String errs(HttpServletRequest request, Model model) {


        return "error";
    }

    @RequestMapping(value = "/jingan/postVol")
    @ResponseBody
    public void sendJinganDeviceHeart() {
        try { logger.error("jingan device[start]");
            List<Sensor> list = sensorService.getSensorsByArea(1);
            List<Sensor> arrays = new ArrayList<Sensor>();
            if(list!=null&&!list.isEmpty()){
                for(Sensor sensor:list){
                    Map<String,Object> map = sensorDeviceLogService.findByMacAndDate(sensor.getMac());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sensor.setBatteryVoltage(String.valueOf(map.get("a")));
                    if(null!=map.get("b")){
                        sensor.setLastSeenTime(sdf.parse(String.valueOf(map.get("b"))));
                    }else{
                        sensor.setLastSeenTime(null);
                    }
                    arrays.add(sensor);
                }
                SendUtils.sendDeviceHeart(arrays);
            }
        }catch(Exception e){
            logger.error("jingan device[error:{}]", e);
        }
    }
}
