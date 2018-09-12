package com.protops.gateway.controller.device;

import com.protops.gateway.constants.DeviceError;
import com.protops.gateway.constants.DeviceErrorMapping;
import com.protops.gateway.domain.iot.ErrorFlow;
import com.protops.gateway.service.ErrorFlowService;
import com.protops.gateway.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonSensorErrorRestController {

    private static final Logger log = LoggerFactory.getLogger(CommonSensorErrorRestController.class);

    @Autowired
    private ErrorFlowService errorFlowService;

    @RequestMapping(value = "rest/sensor/error", method = RequestMethod.GET)
    @ResponseBody
    public Page<ErrorFlow> getErrorlist(@RequestParam(value = "areaId", required = false) Integer areaId,
                                        @RequestParam(value = "p",required = false)Integer p) {
        Page<ErrorFlow> page = new Page<ErrorFlow>();
        try {
            Integer pageSize = 25;
            page.setPageSize(pageSize); //初始化每页条数
            if (p == null) {
                page.setPageNo(1);//初始化页码
            } else {
                page.setPageNo(p);
            }
            page = errorFlowService.pagedMofangList(page,areaId);
            if(page!=null&&page.getResult()!=null){
                for(ErrorFlow errorFlow: page.getResult()){
                    DeviceError deviceError = DeviceErrorMapping.getMappingCode(errorFlow.getErrorBitMap());
                    if(deviceError!=null){
                        errorFlow.setSerialNo(deviceError.getSolution());
                        errorFlow.setErrorBitMap(deviceError.getDesc());
                    }
                }
            }
            return page;
        }catch (Exception e){
            log.error("error:{}",e);
        }
        return page;
    }

}
