package com.protops.gateway.utils.baoxin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.domain.log.SensorOperationLog;
import com.protops.gateway.util.HttpUtils;
import com.protops.gateway.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

public class SendUtils {


    private static final Logger logger = LoggerFactory.getLogger(SendUtils.class);
    //    private static final String url = "http://10.101.2.1:8080/iPlatDAM/service/S_FM_01";
    private static final String url = "http://10.101.2.1:8088/iPlatDAM/service/S_FM_01";
//    private static final String url = "http://112.64.46.113/iPlatDAM/service/S_FM_01";//测试环境
    //   private static final String url = "http://10.105.0.200/iPlatDAM/service/S_FM_01";//正式环境
    public static boolean send(Date ChangeTime,String DeviceId,String SignalStatus,
                               String recordSource,String diTime,String cameraTime,String cameraId,
                               String cph,String cpColor,String status,String picLink){
        try {
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("msg","message");
            data.put("status",0);
            Map<String,Object> blocks = new HashMap<String, Object>();
            Map<String,Object> block1 = new HashMap<String, Object>();
            Map<String,Object> meta = new HashMap<String, Object>();
            meta.put("columns",init());
            List<String[]> list1 = new ArrayList<String[]>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String[] strs = new String[]{simpleDateFormat.format(ChangeTime),
                    DeviceId,
                    SignalStatus,
                    recordSource,
                    diTime,
                    cameraTime,
                    cameraId,
                    cph,
                    cpColor,
                    status,
                    picLink
            };
            list1.add(strs);
            meta.put("columns",init());
            block1.put("rows",list1);
            block1.put("meta",meta);
            blocks.put("block1",block1);
            data.put("blocks",blocks);
            String jsonStr = JSON.toJSONString(data);
            logger.warn("data:{},url:{}",jsonStr,url);
            String res = HttpUtils.postTextJson(url, jsonStr);
            logger.warn(",res:{}",res);
            JSONObject jsonObject = JSONObject.parseObject(res);
            if(0==jsonObject.getInteger("status")){
                return true;
            }
            return false;
        }catch (Exception e){
            logger.error("BaoXin send Error :{}",e);
        }
        return false;
    }

    public static void sendDeviceHeart(List<Sensor> list){
        try {
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("msg","message");
            data.put("status",0);
            Map<String,Object> blocks = new HashMap<String, Object>();
            Map<String,Object> block4 = new HashMap<String, Object>();
            Map<String,Object> meta = new HashMap<String, Object>();
            meta.put("columns",init());
            List<String[]> list1 = new ArrayList<String[]>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(Sensor sensor:list){
                Double d = 0.00;
                if(StringUtils.isNotBlank(sensor.getUid())){
                    d = Double.valueOf(sensor.getUid());
                }else{
                    d = Double.valueOf(sensor.getBatteryVoltage());
                }
                String dumpEnergy = "1";
                if(d<2.00){
                    dumpEnergy = "1";
                }
                if(d>=3.0){
                    sensor.setBatteryVoltage("80");
                }else if(d>=2.8&&d<3.0){
                    sensor.setBatteryVoltage("50");
                }else if(d<2.8){
                    sensor.setBatteryVoltage("20");
                }
                String[] strs = new String[]{simpleDateFormat.format(new Date()),sensor.getMac(),dumpEnergy,sensor.getBatteryVoltage()};
                list1.add(strs);
            }
            meta.put("columns",initHeart());
            block4.put("rows",list1);
            block4.put("meta",meta);
            blocks.put("block4",block4);
            data.put("blocks",blocks);
            String jsonStr = JSON.toJSONString(data);
            logger.warn("data:{},url:{}",jsonStr,url);
            String res = HttpUtils.postTextJson(url, jsonStr);
            logger.warn(",res:{}",res);
        }catch (Exception e){
            logger.error("BaoXin send heart Error :{}",e);
        }
    }

    public static void main(String[] args){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        send(date,"000118061400004F","1","",simpleDateFormat.format(date),
                "","","","","","");
    }

    public static List<PosModel> init(){
        List<PosModel> posModels = new ArrayList<PosModel>();
        PosModel posModel = new PosModel("0","ChangeTime","");
        PosModel posModel2 = new PosModel("1","DeviceId","");
        PosModel posModel3 = new PosModel("2","SignalStatus","");
        PosModel posModel4 = new PosModel("3","recordSource","");
        PosModel posModel5 = new PosModel("4","diTime","");
        PosModel posModel6 = new PosModel("5","cameraTime","");
        PosModel posModel7 = new PosModel("6","cameraId","");
        PosModel posModel8 = new PosModel("7","cph","");
        PosModel posModel9 = new PosModel("8","cpColor","");
        PosModel posModel10 = new PosModel("9","status","");
        PosModel posModel11 = new PosModel("10","picLink","");
        posModels.add(posModel);
        posModels.add(posModel2);
        posModels.add(posModel3);
        posModels.add(posModel4);
        posModels.add(posModel5);
        posModels.add(posModel6);
        posModels.add(posModel7);
        posModels.add(posModel8);
        posModels.add(posModel9);
        posModels.add(posModel10);
        posModels.add(posModel11);
        return posModels;
    }

    public static List<PosModel> initHeart(){
        List<PosModel> posModels = new ArrayList<PosModel>();
        PosModel posModel = new PosModel("0","ChangeTime","");
        PosModel posModel2 = new PosModel("1","DeviceId","");
        PosModel posModel3 = new PosModel("2","dumpEnergy","");
        PosModel posModel4 = new PosModel("3","voltValue","");
        posModels.add(posModel);
        posModels.add(posModel2);
        posModels.add(posModel3);
        posModels.add(posModel4);
        return posModels;
    }

}

class HeartModel{
    private Date ChangeTime;
    private String DeviceId;
    private String dumpEnergy;
    private String voltValue;


    public HeartModel(Date ChangeTime,String DeviceId,String dumpEnergy,String voltValue){
        this.ChangeTime = ChangeTime;
        this.DeviceId = DeviceId;
        this.voltValue = voltValue;
        this.dumpEnergy = dumpEnergy;
    }

    public Date getChangeTime() {
        return ChangeTime;
    }

    public void setChangeTime(Date changeTime) {
        ChangeTime = changeTime;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getDumpEnergy() {
        return dumpEnergy;
    }

    public void setDumpEnergy(String dumpEnergy) {
        this.dumpEnergy = dumpEnergy;
    }

    public String getVoltValue() {
        return voltValue;
    }

    public void setVoltValue(String voltValue) {
        this.voltValue = voltValue;
    }
}

class StatusModel{
    private Date ChangeTime;
    private String DeviceId;
    private String SignalStatus;
    private String recordSource;
    private String diTime;
    private String cameraTime;
    private String cameraId;
    private String cph;
    private String cpColor;
    private String status;
    private String picLink;


    public StatusModel(Date ChangeTime,String DeviceId,String SignalStatus,String recordSource,String diTime,String cameraTime,String cameraId,
                       String cph,String cpColor,String status,String picLink){
        this.ChangeTime = ChangeTime;
        this.DeviceId = DeviceId;
        this.SignalStatus = SignalStatus;
        this.recordSource = recordSource;
        this.diTime = diTime;
        this.cameraTime = cameraTime;
        this.cameraId = cameraId;
        this.cph = cph;
        this.cpColor = cpColor;
        this.status = status;
        this.picLink = picLink;
    }

    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(String recordSource) {
        this.recordSource = recordSource;
    }

    public String getDiTime() {
        return diTime;
    }

    public void setDiTime(String diTime) {
        this.diTime = diTime;
    }

    public String getCameraTime() {
        return cameraTime;
    }

    public void setCameraTime(String cameraTime) {
        this.cameraTime = cameraTime;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getCpColor() {
        return cpColor;
    }

    public void setCpColor(String cpColor) {
        this.cpColor = cpColor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public Date getChangeTime() {
        return ChangeTime;
    }

    public void setChangeTime(Date changeTime) {
        ChangeTime = changeTime;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getSignalStatus() {
        return SignalStatus;
    }

    public void setSignalStatus(String signalStatus) {
        SignalStatus = signalStatus;
    }
}

class StatusModelList{
    private String blockId;
    private List<StatusModel> list;

    public StatusModelList(){
    }

    public StatusModelList(String blockId,List<StatusModel> list){
        this.blockId = blockId;
        this.list = list;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public List<StatusModel> getList() {
        return list;
    }

    public void setList(List<StatusModel> list) {
        this.list = list;
    }
}

class HeartModelList{
    private String blockId;
    private List<HeartModel> list;

    public HeartModelList(){
    }

    public HeartModelList(String blockId,List<HeartModel> list){
        this.blockId = blockId;
        this.list = list;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public List<HeartModel> getList() {
        return list;
    }

    public void setList(List<HeartModel> list) {
        this.list = list;
    }
}

class PosModel{
    private String pos;
    private String name;
    private String descName;

    public PosModel(String pos,String name,String descName){
        this.pos = pos;
        this.name = name;
        this.descName = descName;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescName() {
        return descName;
    }

    public void setDescName(String descName) {
        this.descName = descName;
    }
}