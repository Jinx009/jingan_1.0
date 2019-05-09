package com.protops.gateway.handler.v1_0;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.domain.ReportStatusRequest;
import com.protops.gateway.domain.ReportStatusResponse;
import com.protops.gateway.domain.base.IOTRequest;
import com.protops.gateway.domain.base.IOTResponse;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.exception.IOTException;
import com.protops.gateway.handler.BaseHandler;
import com.protops.gateway.service.SensorService;
import com.protops.gateway.service.device.NewSensorService;
import com.protops.gateway.util.DateUtil;
import com.protops.gateway.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by damen on 2016/3/29.
 */
public class ReportStatusHandler extends BaseHandler<ReportStatusRequest, ReportStatusResponse> {


    @Autowired
    SensorService sensorService;
    @Autowired
    private NewSensorService newSensorService;

    @Override
    public void doPreHandle(IOTRequest<ReportStatusRequest> iotRequest, IOTResponse<ReportStatusResponse> iotResponse, IOTContext iotContext) throws IOTException {

    }

    @Override
    public void doPostHandle(IOTRequest<ReportStatusRequest> iotRequest, IOTResponse<ReportStatusResponse> iotResponse, IOTContext iotContext) throws IOTException {

    }

    @Override
    public void process(IOTRequest<ReportStatusRequest> request, IOTResponse<ReportStatusResponse> response, IOTContext iotContext) throws IOTException {

        ReportStatusRequest reportStatusRequest = request.getDomain();

        Sensor sensorGet = sensorService.getByMac(reportStatusRequest.getMac());
        int type = sensorGet.getAvailable();

        if (sensorGet == null) {
            throw new IOTException(ResponseCodes.Sys.PARAM_ILLEGAL);
        }

        if (StringUtils.isNotBlank(reportStatusRequest.getFrequency())) {
            sensorGet.setFrequency(reportStatusRequest.getFrequency());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getMac())) {
            sensorGet.setMac(reportStatusRequest.getMac());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getAvailable())) {
            sensorGet.setAvailable(Integer.valueOf(reportStatusRequest.getAvailable()));
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getBaseEnergy())) {
            sensorGet.setBaseEnergy(reportStatusRequest.getBaseEnergy());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getBatteryVoltage())) {
            sensorGet.setBatteryVoltage(reportStatusRequest.getBatteryVoltage());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getChannelId())) {
            sensorGet.setChannelId(reportStatusRequest.getChannelId());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getConnected())) {
            sensorGet.setConnected(Integer.valueOf(reportStatusRequest.getConnected()));
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getFirmwareVersion())) {
            sensorGet.setFirmwareVersion(reportStatusRequest.getFirmwareVersion());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getHeartBeatInterval())) {
            sensorGet.setHeartBeatInterval(reportStatusRequest.getHeartBeatInterval());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getPanId())) {
            sensorGet.setPanId(reportStatusRequest.getPanId());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getRouterMac())) {
            sensorGet.setRouterMac(reportStatusRequest.getRouterMac());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getParentMac())) {
            sensorGet.setParentMac(reportStatusRequest.getParentMac());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getXMag())) {
            sensorGet.setXMag(reportStatusRequest.getXMag());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getYMag())) {
            sensorGet.setYMag(reportStatusRequest.getYMag());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getZMag())) {
            sensorGet.setZMag(reportStatusRequest.getZMag());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getType())) {
            sensorGet.setType(reportStatusRequest.getType());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getMode())) {
            sensorGet.setMode(reportStatusRequest.getMode());
        }
        //新
        if(StringUtils.isNotBlank(reportStatusRequest.getLqi())){
            sensorGet.setLqi(reportStatusRequest.getLqi());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getLthreshold())){
            sensorGet.setLthreshold(reportStatusRequest.getLthreshold());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getHthreshold())){
            sensorGet.setHthreshold(reportStatusRequest.getHthreshold());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getWaveHthreshold())){
            sensorGet.setWaveHthreshold(reportStatusRequest.getWaveHthreshold());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getWaveLthreshold())){
            sensorGet.setWaveLthreshold(reportStatusRequest.getWaveLthreshold());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getWindowTime())){
            sensorGet.setWindowTime(reportStatusRequest.getWindowTime());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getSteadyDelayIn())){
            sensorGet.setSteadyDelayIn(reportStatusRequest.getSteadyDelayIn());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getSteadyDelayOut())){
            sensorGet.setSteadyDelayOut(reportStatusRequest.getSteadyDelayOut());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getExtractionInterval())){
            sensorGet.setExtractionInterval(reportStatusRequest.getExtractionInterval());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getZAngleThreshold())){
            sensorGet.setZAngleThreshold(reportStatusRequest.getZAngleThreshold());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getSoltTotalTime())){
            sensorGet.setSoltTotalTime(reportStatusRequest.getSoltTotalTime());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getSoltEqualCopies())){
            sensorGet.setSoltEqualCopies(reportStatusRequest.getSoltEqualCopies());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getSoltPosition())){
            sensorGet.setSoltPosition(reportStatusRequest.getSoltPosition());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getRssi())){
            sensorGet.setRssi(reportStatusRequest.getRssi());
        }
        if(StringUtils.isNotBlank(reportStatusRequest.getLt())){
            sensorGet.setLt(reportStatusRequest.getLt());
        }

        //新增地磁插入时间


        // 这里专门为自己系统做处理
        if (StringUtils.equals(reportStatusRequest.getConnected(), "1")) {
            if (StringUtils.equals(reportStatusRequest.getAvailable(), "1")) {
                Date date = new Date();
                sensorGet.setInTime(date);
                sensorGet.setExpireTime(date);
                sensorGet.setNoticeFlag(0);
                sensorGet.setPaid(1);
                sensorGet.setCurrentMemberId(null);
            }
        }

        sensorGet.setUpdateTime(new Date());


        /**
         * 2017-04-17新增
         */


        if(StringUtils.isNoneBlank(reportStatusRequest.getLogId())){
            sensorGet.setLogId(reportStatusRequest.getLogId());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");


        /**
         * 2019年04月23日
         * 如果新消息和现有状态不一致才更新时间 不一致认为新订单
         */
        if(sensorGet.getAvailable()!=type){

            sensorGet.setVedioStatus("");
            sensorGet.setCph("");
            sensorGet.setCpColor("");
            sensorGet.setCameraId("");
            sensorGet.setCameraName("");
            sensorGet.setPicLink("");
            sensorGet.setVedioTime("");
            if(StringUtils.isNoneBlank(reportStatusRequest.getHappenTime())){
                Date date = DateUtil.parseDate(reportStatusRequest.getHappenTime(),DateUtil.DATE_FMT_DISPLAY);
                sensorGet.setHappenTime(date);
                sensorGet.setLastSeenTime(date);
            }else{
                sensorGet.setHappenTime(new Date());
                sensorGet.setLastSeenTime(new Date());
            }
        }
        sensorGet.setSensorStatus(sensorGet.getAvailable());
        sensorGet.setSensorTime(sdf.format(sensorGet.getHappenTime()));

        sensorGet.setBluetooth("");
        sensorGet.setBluetoothArray("");
        sensorService.save(sensorGet);
        //----浦软推送  start
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            if(1==sensorGet.getAreaId()){
//                List<Sensor> list2 = sensorService.getSensorsByArea(1);
//                int total = list2.size();
//                int av = 0;
//                for(Sensor sensor : list2){
//                    if(sensor.getAvailable()==0){
//                        av++;
//                    }
//                }
//                String json = "{\"Datetime\":\""+sdf.format(new Date())+"\",\"PassType\":\""+sensorGet.getAvailable()+"\"," +
//                        "\"TotalNum\":\""+total+"\",\"iLeftSpace\":\""+av+"\",\"ParkName\":\"爱酷空间地面停车场\"," +
//                        "\"ParkingId\":\""+sensorGet.getMac()+"\"}";
//                HttpUtils.postJson("http://192.168.2.94:8899/zwparking/event",json);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        //-----浦软推送    end

        /**
         * 插入日志部分2019-04-23修改
         */

        newSensorService.saveDeviceLog(sensorGet);
        newSensorService.saveOperationLog(sensorGet);

    }

    @Override
    public IOTRequest<ReportStatusRequest> parseRequest(byte[] requestBody) {
        return JsonUtil.fromJsonWithCustomObject(requestBody, new TypeReference<IOTRequest<ReportStatusRequest>>() {
        });
    }
}
