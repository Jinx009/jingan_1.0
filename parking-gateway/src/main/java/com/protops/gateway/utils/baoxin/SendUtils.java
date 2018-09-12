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
//    private static final String url = "http://10.101.2.1:8088/iPlatDAM/service/S_FM_01";
private static final String url = "http://10.105.0.200/iPlatDAM/service/S_FM_01";
    public static boolean send(List<SensorOperationLog> list){
        if(list!=null&&!list.isEmpty()){
            try {
                Map<String,Object> data = new HashMap<String, Object>();
                data.put("msg","message");
                data.put("status",0);
                Map<String,Object> blocks = new HashMap<String, Object>();
                Map<String,Object> block1 = new HashMap<String, Object>();
                Map<String,Object> meta = new HashMap<String, Object>();
                meta.put("columns",init());
                List<String[]> list1 = new ArrayList<String[]>();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for(SensorOperationLog sensorOperationLog:list){
                    String[] strs = new String[]{simpleDateFormat.format(sensorOperationLog.getChangeTime()),sensorOperationLog.getMac(),String.valueOf(sensorOperationLog.getAvailable())};
                    list1.add(strs);
                }
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
                    dumpEnergy = "0";
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
        List<SensorOperationLog> list = new ArrayList<SensorOperationLog>();
        SensorOperationLog sensorOperationLog = new SensorOperationLog();
        sensorOperationLog.setChangeTime(new Date());
        sensorOperationLog.setMac("123");
        sensorOperationLog.setAvailable(0);
        list.add(sensorOperationLog);
        send(list);
    }

    public static List<PosModel> init(){
        List<PosModel> posModels = new ArrayList<PosModel>();
        PosModel posModel = new PosModel("0","ChangeTime","");
        PosModel posModel2 = new PosModel("1","DeviceId","");
        PosModel posModel3 = new PosModel("2","SignalStatus","");
        posModels.add(posModel);
        posModels.add(posModel2);
        posModels.add(posModel3);
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


    public StatusModel(Date ChangeTime,String DeviceId,String SignalStatus){
        this.ChangeTime = ChangeTime;
        this.DeviceId = DeviceId;
        this.SignalStatus = SignalStatus;
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