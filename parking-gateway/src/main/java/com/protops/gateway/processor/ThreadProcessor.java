package com.protops.gateway.processor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by fanlin on 16/6/15.
 */
public class ThreadProcessor implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    //线程分五组轮循sensor
    Integer groupCount = 1;

    ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

    public void start(){

        System.out.println("ThreadProcessor start...");

        // 分组扫描sensor表记录，查看是否有需要通知的记录
        // 5分钟之后，开始轮循；每隔1分钟，轮循一次
        for(int i = 1; i <= groupCount; i++ ){

            service.scheduleWithFixedDelay(new PollingParkinglotProcessor(applicationContext, 1), 1, 5, TimeUnit.SECONDS);
        }

        service.scheduleWithFixedDelay(new NoticeProcessor(applicationContext), 1, 5, TimeUnit.SECONDS);
//        service.scheduleWithFixedDelay(new NoticeExpiringProcessor(applicationContext), 30, 30, TimeUnit.SECONDS);
//        service.scheduleWithFixedDelay(new NoticeExpiredProcessor(applicationContext), 30, 30, TimeUnit.SECONDS);
    }



    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
