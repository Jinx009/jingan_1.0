package com.protops.gateway.task;

import com.alibaba.fastjson.JSONArray;
import com.protops.gateway.dao.log.IntersectionSensorOperationLogDao;
import com.protops.gateway.domain.log.IntersectionSensorOperationLog;
import com.protops.gateway.util.HttpUtil;
import com.protops.gateway.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by jinx on 3/14/17.
 */
@Component
@Lazy(value=false)
public class IntersectionSensorOperationLogSynTask {

    private static final Logger logger = LoggerFactory.getLogger(IntersectionSensorOperationLogSynTask.class);

    @Autowired
    private IntersectionSensorOperationLogDao intersectionSensorOperationLogDao;

    public static final String LOG_FROM_URL = "http://zhanway-sh.6655.la:8899/zhanway-parkinglot/vehicleDataLog/logs";

    @Scheduled(fixedRate = 60*1000)//每一分钟执行一次
    public void syc() throws Exception {
//        String dateStr = intersectionSensorOperationLogDao.getSycNewTime();
//        logger.warn("[info][IntersectionSensorOperationLogSynTask.syc]dateStr:{}",dateStr);
//        String postUrl = LOG_FROM_URL+"?dateStr="+dateStr;
//        String logs = HttpUtil.post(postUrl, "");//HttpUtil.post(postUrl, "");
//        if(StringUtils.isNotBlank(logs)){
//            try {
//                List<IntersectionSensorOperationLog> list = JSONArray.parseArray(logs,IntersectionSensorOperationLog.class);
//                if(list!=null&&!list.isEmpty()){
//                    for(IntersectionSensorOperationLog intersectionSensorOperationLog:list){
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        intersectionSensorOperationLog.setCreateTime(sdf.parse("2016-01-01 01:01:01"));
//                        intersectionSensorOperationLogDao.save(intersectionSensorOperationLog);
//                    }
//                }
//            }catch (Exception e){
//                logger.warn("[error][IntersectionSensorOperationLogSynTask.syc]dateStr:{}",dateStr);
//            }
//        }
    }


    //@Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点整
    //@Scheduled(cron = "0 30 0 * * ?")//每天凌晨0点30分
    //@Scheduled(cron = "0 */60 * * * ?")//1小时处理一次

}
