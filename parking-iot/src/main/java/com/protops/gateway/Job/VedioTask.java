package com.protops.gateway.Job;

import com.protops.gateway.dao.log.SensorOperationLogDao;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.domain.log.SensorOperationLog;
import com.protops.gateway.service.SensorService;
import com.protops.gateway.util.HttpUtils;
import com.protops.gateway.utils.baoxin.SendUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
@Lazy(value = false)
public class VedioTask {

    private static final Logger log = LoggerFactory.getLogger(VedioTask.class);
    private static final String VEDIO_URL = "http://58.246.184.99:8089/d/saveVedio";
    private static final String VEDIO_URL_IMG = "http://58.246.184.99:801/";

    @Autowired
    private SensorService sensorService;
    @Autowired
    private SensorOperationLogDao sensorOperationLogDao;


    @Scheduled(fixedRate = 90 * 1000) // 每1.5分钟S执行一次
    public void sendNormal() {
        try {
           SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
           String[] macs = new String[]{"0001180614000062",
                   "0001180614000120",
                   "0001180614000052",
                   "0001180614000233",
                   "0001180614000011",
                   "00011806140000A0",
                   "00011806140000A6",
                   "000118061400007A",
                   "0001180614000058",
                   "0001180614000055",
                   "00011806140000B4"};
           for(String s:macs){
               Sensor sensor = sensorService.getByMac(s);
               //地磁三分钟后仍收不到视频信息录制一段
                if("".equals(sensor.getCph())&&"".equals(sensor.getPicLink())){
                    Date date = sensor.getHappenTime();
                    Date now = new Date();
                    if((now.getTime()-date.getTime())>180000){//3分钟
                        String eventTime = sdf.format(date);
                        HttpUtils.get(VEDIO_URL+"?mac="+sensor.getMac()+"&eventTime="+eventTime+"&status="+sensor.getAvailable());
                        SensorOperationLog log = new SensorOperationLog();
                        String picLink = VEDIO_URL_IMG+""+sdf2.format(date)+"/"+sensor.getMac()+"_"+eventTime;
                        log.setMac(sensor.getMac());
                        log.setChangeTime(date);
                        log.setAvailable(sensor.getAvailable());
                        log.setFailTimes(0);
                        log.setSendStatus(0);
                        log.setAreaId(sensor.getAreaId());
                        log.setCreateTime(new Date());
                        log.setCameraId("");
                        log.setCpColor("白色");
                        log.setCph(getCph());
                        log.setPicLink(picLink);
                        log.setType(2);
                        log.setDescription(sensor.getDesc());
                        log.setStatus(String.valueOf(sensor.getAvailable()));
                        sensor.setPicLink(picLink);
                        sensorService.update(sensor);
                        sensorOperationLogDao.save(log);
                        boolean res = SendUtils.send(sensor.getHappenTime(), sensor.getMac(), String.valueOf(sensor.getAvailable()),
                                "", sensor.getSensorTime(), sensor.getSensorTime(), "",
                                log.getCph(), log.getCpColor(), log.getStatus(), log.getPicLink());
                        if(res){
                            log.setSendStatus(1);
                            log.setSendTime(new Date());
                            sensorOperationLogDao.update(log);
                        }else{
                            log = sensorOperationLogDao.get(log.getId());
                            log.setFailTimes(log.getFailTimes() + 1);
                            sensorOperationLogDao.update(log);
                        }
                    }
                }
                //视频停满三分钟
                if(!"".equals(sensor.getCph())&&1==sensor.getAvailable()){
                    Date date = sensor.getHappenTime();
                    Date now = new Date();
                    long sub_time = (now.getTime()-date.getTime())/1000;
                    if(sub_time>180){//视频停满三分钟
                        String eventTime = sdf.format(date);
                        HttpUtils.get(VEDIO_URL+"?mac="+sensor.getMac()+"&eventTime="+eventTime+"&status=2");
                        SendUtils.send(sensor.getHappenTime(), sensor.getMac(), String.valueOf(sensor.getAvailable()),
                                "", sensor.getSensorTime(), sensor.getSensorTime(), "",
                                sensor.getCph(), sensor.getCpColor(),"2", sensor.getPicLink());
                    }
                }
           }
        } catch (Exception e) {
            log.error("error:{}", e);
        }
    }

    private static String getCph(){
        String str2 = "ABCDEFGHJKLPQS";
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<4;i++){
            int number=random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return "沪"+str2.charAt(random.nextInt(14))+sb.toString()+"警";
    }

}
