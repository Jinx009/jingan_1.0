package com.protops.gateway.service.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.constants.common.SignConstant;
import com.protops.gateway.dao.JobDao;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.iot.Job;
import com.protops.gateway.service.BaseService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jinx on 3/13/17.
 */
@Setter
@Service
public class NewJobService extends BaseService{

    @Autowired
    private JobDao jobDao;
    @Autowired
    private SignService signService;

    private AjaxResponse ajaxResponse;

    /**
     * 更新任务状态
     * @param params
     * @return
     */
    public AjaxResponse status(String params){
        JSONObject jsonObject = JSONObject.parseObject(params);
        ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, SignConstant.SIGN_FAIL_MSG,null);
        if(signService.checkSign(jsonObject)) {
            List<Job> list = JSONArray.parseArray(jsonObject.getString(HttpConstant.DATA),Job.class);
            if(list!=null&&!list.isEmpty()){
                for(Job job:list){
                    Job job1 = jobDao.findById(job.getId());
                    if(job1!=null){
                        job1.setStatus(job.getStatus());
                        jobDao.update(job1);
                    }
                }
            }
            ajaxResponse.setRespCode(HttpConstant.OK_CODE);
            ajaxResponse.setMsg(HttpConstant.OK_MSG);
        }
        return ajaxResponse;
    }

}
