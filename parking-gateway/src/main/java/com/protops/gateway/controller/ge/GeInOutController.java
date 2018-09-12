package com.protops.gateway.controller.ge;

import com.protops.gateway.domain.log.SensorInOutLog;
import com.protops.gateway.service.device.GeCarInOutService;
import com.protops.gateway.util.DateUtil;
import com.protops.gateway.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by jinx on 4/13/17.
 */
@Controller
public class GeInOutController {

    @Autowired
    private GeCarInOutService geCarInOutService;

    @RequestMapping(value = "/ge/carInOutLog")
    @ResponseBody
    private List<SensorInOutLog> getByArea(@RequestParam(value = "areaId",required = false)Integer areaId,
                                           @RequestParam(value = "dateStr",required = false)String dateStr,
                                           @RequestParam(value = "type",required = false)Integer type){
        if(!StringUtils.isNotBlank(dateStr)){
            dateStr = DateUtil.getDate(new Date(), DateUtil.DATE_FMT_DISPLAY2);
        }
        if(type == 1){
            return geCarInOutService.getByArea(dateStr,areaId);
        }else if(type == 2){
            return geCarInOutService.getByAreaMonth(dateStr,areaId);
        }else if(type == 3){
            return geCarInOutService.getByAreaSimpleMonth(dateStr,areaId);
        }else{
            return null;
        }
    }

}
