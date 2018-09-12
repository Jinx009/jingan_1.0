package com.protops.gateway.service.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.constants.common.SignConstant;
import com.protops.gateway.dao.device.IntersectionSensorDao;
import com.protops.gateway.dao.log.IntersectionSensorDeviceLogDao;
import com.protops.gateway.dao.log.IntersectionSensorOperationLogDao;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.device.IntersectionSensor;
import com.protops.gateway.domain.log.IntersectionSensorDeviceLog;
import com.protops.gateway.domain.log.IntersectionSensorOperationLog;
import com.protops.gateway.service.BaseService;
import com.protops.gateway.service.common.SignService;
import com.protops.gateway.util.StringUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by jinx on 3/13/17.
 */
@Service("intersectionSensorService")
@Setter
public class IntersectionSensorService extends BaseService{

    @Autowired
    private SignService signService;
    @Autowired
    private IntersectionSensorDeviceLogDao intersectionSensorDeviceLogDao;
    @Autowired
    private IntersectionSensorOperationLogDao intersectionSensorOperationLogDao;
    @Autowired
    private IntersectionSensorDao intersectionSensorDao;

    private AjaxResponse ajaxResponse;

    /**
     * 路口地磁注册
     * @param params
     * @return
     */
    public AjaxResponse register(String params){
        JSONObject jsonObject = JSONObject.parseObject(params);
        ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, SignConstant.SIGN_FAIL_MSG,null);
        if(signService.checkSign(jsonObject)){
            List<IntersectionSensor> list = JSONArray.parseArray(jsonObject.getString(HttpConstant.DATA),IntersectionSensor.class);
            if(list!=null&&!list.isEmpty()){
                for(IntersectionSensor intersectionSensor:list){
                    IntersectionSensor intersectionSensor1 = intersectionSensorDao.findByMac(intersectionSensor.getMac());
                    if(intersectionSensor1==null){
                        intersectionSensor.setCreateTime(new Date());
                        intersectionSensor.setTestTime(new Date());
                        intersectionSensor.setUpdateTime(new Date());
                        intersectionSensorDao.save(intersectionSensor);
                    }
                }
            }
            ajaxResponse.setRespCode(HttpConstant.OK_CODE);
            ajaxResponse.setMsg(HttpConstant.OK_MSG);
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
            List<IntersectionSensorDeviceLog> list = JSONArray.parseArray(jsonObject.getString(HttpConstant.DATA),IntersectionSensorDeviceLog.class);
            if(list!=null&&!list.isEmpty()){
                for(IntersectionSensorDeviceLog intersectionSensorDeviceLog:list){
                    intersectionSensorDeviceLog.setCreateTime(new Date());
                    intersectionSensorDeviceLogDao.save(intersectionSensorDeviceLog);

                    IntersectionSensor intersectionSensor = intersectionSensorDao.findByMac(intersectionSensorDeviceLog.getMac());
                    if(intersectionSensor!=null){
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getUid()))
                            intersectionSensor.setUid(intersectionSensorDeviceLog.getUid());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getDesc()))
                            intersectionSensor.setDesc(intersectionSensorDeviceLog.getDesc());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getFirmwareVersion()))
                            intersectionSensor.setFirmwareVersion(intersectionSensorDeviceLog.getFirmwareVersion());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getBatteryVoltage()))
                            intersectionSensor.setBatteryVoltage(intersectionSensorDeviceLog.getBatteryVoltage());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getXMag()))
                            intersectionSensor.setXMag(intersectionSensorDeviceLog.getXMag());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getYMag()))
                            intersectionSensor.setYMag(intersectionSensorDeviceLog.getYMag());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getZMag()))
                            intersectionSensor.setZMag(intersectionSensorDeviceLog.getZMag());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getBaseEnergy()))
                            intersectionSensor.setBaseEnergy(intersectionSensorDeviceLog.getBaseEnergy());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getHeartBeatInterval()))
                            intersectionSensor.setHardwareVersion(intersectionSensorDeviceLog.getHardwareVersion());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getChannelId()))
                            intersectionSensor.setChannelId(intersectionSensorDeviceLog.getChannelId());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getPanId()))
                            intersectionSensor.setPanId(intersectionSensorDeviceLog.getPanId());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getFrequency()))
                            intersectionSensor.setFrequency(intersectionSensorDeviceLog.getFrequency());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getParentMac()))
                            intersectionSensor.setParentMac(intersectionSensorDeviceLog.getMac());
                        if(intersectionSensorDeviceLog.getConnected()!=null)
                            intersectionSensor.setConnected(intersectionSensorDeviceLog.getConnected());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getType()))
                            intersectionSensor.setType(intersectionSensorDeviceLog.getType());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getRouterMac()))
                            intersectionSensor.setRouterMac(intersectionSensorDeviceLog.getRouterMac());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getMode()))
                            intersectionSensor.setMode(intersectionSensorDeviceLog.getMode());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getAddr()))
                            intersectionSensor.setAddr(intersectionSensorDeviceLog.getAddr());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getHardwareVersion()))
                            intersectionSensor.setHardwareVersion(intersectionSensorDeviceLog.getHardwareVersion());
                        if(intersectionSensorDeviceLog.getTestTime()!=null)
                            intersectionSensor.setTestTime(intersectionSensorDeviceLog.getTestTime());

                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getLqi()))
                            intersectionSensor.setLqi(intersectionSensorDeviceLog.getLqi());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getLthreshold()))
                            intersectionSensor.setLthreshold(intersectionSensorDeviceLog.getLthreshold());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getHthreshold()))
                            intersectionSensor.setHthreshold(intersectionSensorDeviceLog.getHthreshold());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getWaveHthreshold()))
                            intersectionSensor.setWaveHthreshold(intersectionSensorDeviceLog.getWaveHthreshold());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getWaveLthreshold()))
                            intersectionSensor.setWaveLthreshold(intersectionSensorDeviceLog.getWaveLthreshold());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getWindowTime()))
                            intersectionSensor.setWindowTime(intersectionSensorDeviceLog.getWindowTime());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getSteadyDelayIn()))
                            intersectionSensor.setSteadyDelayIn(intersectionSensorDeviceLog.getSteadyDelayIn());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getSteadyDelayOut()))
                            intersectionSensor.setSteadyDelayOut(intersectionSensorDeviceLog.getSteadyDelayOut());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getExtractionInterval()))
                            intersectionSensor.setExtractionInterval(intersectionSensorDeviceLog.getExtractionInterval());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getZAngleThreshold()))
                            intersectionSensor.setZAngleThreshold(intersectionSensorDeviceLog.getZAngleThreshold());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getSoltTotalTime()))
                            intersectionSensor.setSoltTotalTime(intersectionSensorDeviceLog.getSoltTotalTime());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getSoltEqualCopies()))
                            intersectionSensor.setSoltEqualCopies(intersectionSensorDeviceLog.getSoltEqualCopies());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getSoltPosition()))
                            intersectionSensor.setSoltPosition(intersectionSensorDeviceLog.getSoltPosition());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getRssi()))
                            intersectionSensor.setRssi(intersectionSensorDeviceLog.getRssi());
                        if(StringUtils.isNotBlank(intersectionSensorDeviceLog.getLt()))
                            intersectionSensor.setLt(intersectionSensorDeviceLog.getLt());


                        intersectionSensor.setUpdateTime(new Date());

                        intersectionSensorDao.update(intersectionSensor);
                    }
                }
            }
            ajaxResponse.setMsg(HttpConstant.OK_MSG);
            ajaxResponse.setRespCode(HttpConstant.OK_CODE);
        }
        return ajaxResponse;
    }

    /**
     * 业务数据传输
     * @param params
     * @return
     */
    public AjaxResponse operation(String params){
        JSONObject jsonObject = JSONObject.parseObject(params);
        ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, SignConstant.SIGN_FAIL_MSG,null);
        if(signService.checkSign(jsonObject)){
            List<IntersectionSensorOperationLog> list = JSONArray.parseArray(jsonObject.getString(HttpConstant.DATA),IntersectionSensorOperationLog.class);
            if(list!=null&&!list.isEmpty()){
                for(IntersectionSensorOperationLog intersectionSensorOperationLog:list){
                    IntersectionSensor intersectionSensor = intersectionSensorDao.findByMac(intersectionSensorOperationLog.getMac());
                    if(intersectionSensor!=null) {
                        intersectionSensorOperationLog.setAreaId(intersectionSensor.getAreaId());

                        intersectionSensor.setUpdateTime(new Date());
                        if(intersectionSensorOperationLog.getLid()!=null)
                            intersectionSensor.setLid(intersectionSensorOperationLog.getLid());
                        if(StringUtils.isNotBlank(intersectionSensorOperationLog.getPos()))
                            intersectionSensor.setPos(intersectionSensorOperationLog.getPos());
                        if(StringUtils.isNotBlank(intersectionSensorOperationLog.getDirection()))
                            intersectionSensor.setDirection(intersectionSensorOperationLog.getDirection());
                        if(StringUtils.isNotBlank(intersectionSensorOperationLog.getInOrOut()))
                            intersectionSensor.setInOrOut(intersectionSensorOperationLog.getInOrOut());
                        if(StringUtils.isNotBlank(intersectionSensorOperationLog.getLidIndirection()))
                            intersectionSensor.setLidIndirection(intersectionSensorOperationLog.getLidIndirection());
                        if(StringUtils.isNotBlank(intersectionSensorOperationLog.getDirectionOnLid()))
                            intersectionSensor.setDirectionOnLid(intersectionSensorOperationLog.getDirectionOnLid());
                        intersectionSensorDao.update(intersectionSensor);
                    }
                    intersectionSensorOperationLog.setCreateTime(new Date());
                    intersectionSensorOperationLogDao.save(intersectionSensorOperationLog);
                }
            }
            ajaxResponse.setMsg(HttpConstant.OK_MSG);
            ajaxResponse.setRespCode(HttpConstant.OK_CODE);
        }
        return ajaxResponse;
    }

}
