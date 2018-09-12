package com.protops.gateway.controller.device;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.protops.gateway.constants.Constants;
import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.constants.enums.iot.JobStatus;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.iot.Job;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.service.SensorService;
import com.protops.gateway.service.common.TokenFactoryService;
import com.protops.gateway.service.device.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class CommonLockRestController {

    private static final Logger log = LoggerFactory.getLogger(CommonLockRestController.class);

    @Autowired
    private TokenFactoryService tokenFactoryService;
    @Autowired
    private JobService jobService;
    @Autowired
    private SensorService sensorService;


    /**
     * 升降地锁
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "rest/lock/status", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public AjaxResponse status(@RequestBody byte[] requestBody) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        try {
            String jsonData = new String(requestBody, Constants.Default_SysEncoding);
            log.warn("data [req:{}]", jsonData);
            JSONObject jsonObject = JSONObject.parseObject(jsonData);
            String token = jsonObject.getString(HttpConstant.TOKEN);
            if (tokenFactoryService.checkToken(token)) {
                String mac = jsonObject.getString("mac");
                Integer status = jsonObject.getInteger("status");
                Sensor sensor = sensorService.getByMac(mac);
                List<Job> jobs = jobService.getRunningJobByRouter(JobStatus.getUndoingStatus(),sensor.getRouterMac());
                if(status!=0&&status!=1){
                    ajaxResponse.setRespCode("300500");
                    ajaxResponse.setMsg("指令参数不合法！");
                    return ajaxResponse;
                }else if(sensor==null){
                    ajaxResponse.setRespCode("300404");
                    ajaxResponse.setMsg("地锁不存在！");
                    return ajaxResponse;
                }else if(jobs!=null&&!jobs.isEmpty()) {
                    ajaxResponse.setRespCode("3001");
                    ajaxResponse.setMsg("当前还有未执行完成的升降操作！");
                    return ajaxResponse;
                }else if(1==sensor.getAvailable()&&"1".equals(status)){
                    ajaxResponse.setRespCode("300502");
                    ajaxResponse.setMsg("当前车位有车，不允许升起地锁！");
                    return ajaxResponse;
                }else{
                    Map<String,Object> data = new HashMap<String, Object>();
                    data.put("mac",mac);
                    data.put("data",status);
                    data.put("type","A");
                    Job job = new Job();
                    job.setCmd("cfglock");
                    job.setTarget(sensor.getRouterMac());
                    job.setJobDetail(JSON.toJSONString(data));
                    jobService.save(job);
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG,null);
                    return ajaxResponse;
                }
            }else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            log.error("error [error:{}]", e);
        }
        return ajaxResponse;
    }

}
