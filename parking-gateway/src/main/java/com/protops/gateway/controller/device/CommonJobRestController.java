package com.protops.gateway.controller.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.constants.enums.iot.JobStatus;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.iot.Job;
import com.protops.gateway.service.device.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CommonJobRestController {

    @Autowired
    private JobService jobService;

    private static final Logger logger = LoggerFactory.getLogger(CommonJobRestController.class);

    @RequestMapping(value = "/rest/job/save",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public AjaxResponse save(@RequestBody String requestBody) throws Exception{
        logger.warn("[data:{}", requestBody);
        JSONObject jsonObject = JSON.parseObject(requestBody);
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        try {
            String mac = jsonObject.getString("mac");
            String cmd = jsonObject.getString("cmd");
            List<Job> jobList = jobService.getRunningJobByRouter(JobStatus.getUndoingStatus(),mac);
            if (jobList != null && jobList.size() != 0) {
                ajaxResponse.setRespCode("400");
                ajaxResponse.setMsg("有任务尚未完成!");
            }else{
                Job job = new Job();
                job.setCmd(cmd);
                job.setTarget(mac);
                job.setJobDetail(jsonObject.getString("obj"));
                jobService.save(job);
                ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, job);
            }
        }catch (Exception e){
            logger.error("error:{}",e);
        }
        return ajaxResponse;
    }

    @RequestMapping(value = "/rest/job/find",method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse save(Integer id) throws Exception{
        logger.warn("[data:{}", id);
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        try {
            Job job = jobService.getById(id);
            ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, job);
        }catch (Exception e){
            logger.error("error:{}",e);
        }
        return ajaxResponse;
    }

}
