package com.protops.gateway.controller.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protops.gateway.constants.Constants;
import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.domain.AppInfo;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.common.LocationNoticeVo;
import com.protops.gateway.domain.common.TokenFactory;
import com.protops.gateway.domain.device.LocationMacVo;
import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.domain.log.SensorUploadOperationLog;
import com.protops.gateway.service.AppInfoService;
import com.protops.gateway.service.LocationService;
import com.protops.gateway.service.common.TokenFactoryService;
import com.protops.gateway.service.device.LocationMacVoService;
import com.protops.gateway.service.device.SensorUploadOperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by jinx on 4/19/17.
 */
@Controller
public class CommonSensorNoticeRestController {

    private static final Logger logger = LoggerFactory.getLogger(CommonSensorNoticeRestController.class);

    @Autowired
    private TokenFactoryService tokenFactoryService;
    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private SensorUploadOperationLogService sensorUploadOperationLogService;
    @Autowired
    private LocationMacVoService locationMacVoService;

    /**
     * 开启和关闭
     * @param status
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "rest/notice/{status}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public AjaxResponse noticeStatus(@PathVariable(value = "status") Integer status,
                                     @RequestBody byte[] requestBody) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        try {
            String jsonData = new String(requestBody, Constants.Default_SysEncoding);
            logger.warn("CommonSensorNoticeRestController.noticeStatus [req:{}]", jsonData);
            JSONObject jsonObject = JSONObject.parseObject(jsonData);
            String token = jsonObject.getString(HttpConstant.TOKEN);
            if (tokenFactoryService.checkToken(token)) {
                TokenFactory tokenFactory = tokenFactoryService.getToken(token);
                AppInfo appInfo = appInfoService.getByAppId(tokenFactory.getBaseId());
               if(0==status){
                   List<Location> list = locationService.findByAppInfoId(appInfo.getId());
                   if(list!=null&&!list.isEmpty()){
                       for(Location location:list){
                           location.setNoticeType(0);
                           locationService.update(location);
                       }
                   }
                   ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG,null);
               }else if(1==status){
                   List<Location> list = locationService.findByAppInfoId(appInfo.getId());
                   List<LocationNoticeVo> list2 = JSONArray.parseArray(jsonObject.getString(HttpConstant.PARAMS),LocationNoticeVo.class);
                   if(list!=null&&list2!=null&&!list.isEmpty()&&!list2.isEmpty()){
                        for(LocationNoticeVo locationNoticeVo : list2){
                            boolean checkLocation = false;
                            for (Location location : list){
                                if(location.getId()==locationNoticeVo.getId()){
                                    checkLocation = true;
                                }
                            }
                            if(!checkLocation){
                                ajaxResponse.setMsg(HttpConstant.LCOATION_FAIL);
                                return ajaxResponse;
                            }
                        }
                        for(Location location:list){
                           location.setNoticeType(0);
                           locationService.update(location);
                        }
                       for(LocationNoticeVo locationNoticeVo : list2){
                           for (Location location : list){
                               if(location.getId()==locationNoticeVo.getId()){
                                   location.setNoticeType(locationNoticeVo.getConfig());
                                   locationService.update(location);
                               }
                           }
                       }
                       ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG,null);
                   }else{
                       ajaxResponse.setMsg(HttpConstant.LCOATION_FAIL);
                   }
               }
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("CommonSensorNoticeRestController.noticeStatus [error:{}]", e);
        }
        logger.warn("CommonSensorNoticeRestController.noticeStatus[res:{}]",ajaxResponse);
        return ajaxResponse;
    }

    /**
     * 真实车位状态上报
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "rest/sensor/uploadStatus", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public AjaxResponse sensorStatusUpload(@RequestBody byte[] requestBody) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        try {
            String jsonData = new String(requestBody, Constants.Default_SysEncoding);
            logger.warn("CommonSensorNoticeRestController.sensorStatusUpload [req:{}]", jsonData);
            JSONObject jsonObject = JSONObject.parseObject(jsonData);
            String token = jsonObject.getString(HttpConstant.TOKEN);
            if (tokenFactoryService.checkToken(token)) {
                SensorUploadOperationLog sensorUploadOperationLog = JSONObject.parseObject(jsonObject.getString(HttpConstant.PARAMS),SensorUploadOperationLog.class);
                sensorUploadOperationLog.setCreateTime(new Date());
                sensorUploadOperationLogService.save(sensorUploadOperationLog);
                ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG,null);
            }else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("CommonSensorNoticeRestController.sensorStatusUpload [error:{}]", e);
        }
        logger.warn("CommonSensorNoticeRestController.sensorStatusUpload[res:{}]",ajaxResponse);
        return ajaxResponse;
    }


    /**
     * 返回4列对应关系
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "rest/data", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public AjaxResponse data(@RequestBody byte[] requestBody) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        try {
            String jsonData = new String(requestBody, Constants.Default_SysEncoding);
            logger.warn("CommonSensorNoticeRestController.data [req:{}]", jsonData);
            JSONObject jsonObject = JSONObject.parseObject(jsonData);
            String token = jsonObject.getString(HttpConstant.TOKEN);
            if (tokenFactoryService.checkToken(token)) {
                TokenFactory tokenFactory = tokenFactoryService.getToken(token);
                AppInfo appInfo = appInfoService.getByAppId(tokenFactory.getBaseId());
                List<LocationMacVo> list = locationMacVoService.findByAppId(appInfo.getId());
                ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG,list);
            }else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("CommonSensorNoticeRestController.data [error:{}]", e);
        }
        logger.warn("CommonSensorNoticeRestController.data[res:{}]",ajaxResponse);
        return ajaxResponse;
    }
}
