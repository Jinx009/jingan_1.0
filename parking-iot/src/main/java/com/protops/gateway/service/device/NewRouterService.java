package com.protops.gateway.service.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.constants.common.SignConstant;
import com.protops.gateway.dao.JobDao;
import com.protops.gateway.dao.RouterDao;
import com.protops.gateway.dao.log.RouterDeviceLogDao;
import com.protops.gateway.domain.HeartBeatResponse;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.common.TokenFactory;
import com.protops.gateway.domain.iot.Job;
import com.protops.gateway.domain.iot.Router;
import com.protops.gateway.domain.log.RouterDeviceLog;
import com.protops.gateway.service.BaseService;
import com.protops.gateway.service.common.SignService;
import com.protops.gateway.service.common.TokenFactoryService;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.util.StringUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jinx on 3/13/17.
 */
@Service("newRouterService")
@Setter
public class NewRouterService extends BaseService{

    @Autowired
    private SignService signService;
    @Autowired
    private RouterDao routerDao;
    @Autowired
    private TokenFactoryService tokenFactoryService;
    @Autowired
    private RouterDeviceLogDao routerDeviceLogDao;
    @Autowired
    private JobDao jobDao;

    private AjaxResponse ajaxResponse;

    /**
     * 接收机注册获取新的token
     * @param params
     * @return
     */
    public AjaxResponse register(String params){
        JSONObject jsonObject = JSONObject.parseObject(params);
        ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, SignConstant.SIGN_FAIL_MSG,null);
        if(signService.checkSign(jsonObject)){
            Router router = JSON.parseObject(jsonObject.getString(HttpConstant.DATA), Router.class);
            Router baseRouter = routerDao.getByMac(router.getMac());
            if(baseRouter!=null){
                ajaxResponse.setMsg(HttpConstant.DEVICE_EXIST);
            }else{
                TokenFactory tokenFactory = tokenFactoryService.createNew(router.getMac(), 1);
                router.setCreateTime(new Date());
                router.setUpdateTime(new Date());
                router.setRecSt(1);
                routerDao.save(router);
                ajaxResponse.setDomain(tokenFactory.getSecret());
                ajaxResponse.setMsg(HttpConstant.REGISTER_FIAL_SUCCESS);
                ajaxResponse.setRespCode(HttpConstant.OK_CODE);
            }
        }
        return ajaxResponse;
    }

    /**
     * 心跳
     * @param params
     * @return
     */
    public AjaxResponse device(String params){
        JSONObject jsonObject = JSONObject.parseObject(params);
        ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, SignConstant.SIGN_FAIL_MSG,null);
        if(signService.checkSign(jsonObject)){
            RouterDeviceLog routerDeviceLog = JSON.parseObject(jsonObject.getString(HttpConstant.DATA),RouterDeviceLog.class);
            routerDeviceLog.setCreateTime(new Date());
            routerDeviceLogDao.save(routerDeviceLog);

            Router router = routerDao.getByMac(routerDeviceLog.getMac());
            if(router!=null){
                router.setUpdateTime(new Date());
                if(StringUtils.isNotBlank(routerDeviceLog.getPanId()))
                    router.setPanId(routerDeviceLog.getPanId());
                if(StringUtils.isNotBlank(routerDeviceLog.getChannelId()))
                    router.setChannelId(routerDeviceLog.getChannelId());
                if(StringUtils.isNotBlank(routerDeviceLog.getHeartbeatInterval()))
                    router.setHeartbeatInterval(routerDeviceLog.getHeartbeatInterval());
                if(StringUtils.isNotBlank(routerDeviceLog.getInterfaceVersion()))
                    router.setInterfaceVersion(routerDeviceLog.getInterfaceVersion());
                if(StringUtils.isNotBlank(routerDeviceLog.getFirmwareVersion()))
                    router.setFirmwareVersion(routerDeviceLog.getFirmwareVersion());
                if(StringUtils.isNotBlank(routerDeviceLog.getRouterFirmwareVersion()))
                    router.setRouterFirmwareVersion(routerDeviceLog.getRouterFirmwareVersion());
                if(StringUtils.isNotBlank(routerDeviceLog.getFrequency()))
                    router.setFrequency(routerDeviceLog.getFrequency());
                if(routerDeviceLog.getStatus()!=null)
                    router.setStatus(routerDeviceLog.getStatus());
                if(routerDeviceLog.getOperation()!=null)
                    router.setOperation(routerDeviceLog.getOperation());
//                if(StringUtils.isNotBlank(routerDeviceLog.getSecret()))
//                    router.setSecret(routerDeviceLog.getSecret());
//                if(StringUtils.isNotBlank(routerDeviceLog.getNote()))
//                    router.setNote(routerDeviceLog.getNote());
                if(routerDeviceLog.getLastSeenTime()!=null)
                    router.setLastSeenTime(routerDeviceLog.getLastSeenTime());
                if(StringUtils.isNotBlank(routerDeviceLog.getHardwareVersion()))
                    router.setHardwareVersion(routerDeviceLog.getHardwareVersion());
                if(StringUtils.isNotBlank(routerDeviceLog.getRHardwareVersion()))
                    router.setRHardwareVersion(routerDeviceLog.getRHardwareVersion());

                routerDao.update(router);
            }
            List<Integer> integerList = new ArrayList<Integer>();
            integerList.add(0);
            List<Job> jobList = jobDao.getByMac(routerDeviceLog.getMac(),integerList);
            List<HeartBeatResponse> list = new ArrayList<HeartBeatResponse>();
            if(jobList!=null&&!jobList.isEmpty()){
                for(Job job:jobList){
                    HeartBeatResponse heartBeatResponse = new HeartBeatResponse();
                    heartBeatResponse.setCmd(job.getCmd());
                    heartBeatResponse.setJobId(String.valueOf(job.getId()));
                    heartBeatResponse.setJobDetail(JsonUtil.fromJson(job.getJobDetail(), Map.class));
                    list.add(heartBeatResponse);
                }
            }
//            JsonUtil.fromJson(job.getJobDetail(), Map.class)
            ajaxResponse.setDomain(list);
            ajaxResponse.setRespCode(HttpConstant.OK_CODE);
            ajaxResponse.setMsg(HttpConstant.OK_MSG);
        }
        return ajaxResponse;
    }
}
