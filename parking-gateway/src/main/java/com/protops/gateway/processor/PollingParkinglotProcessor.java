package com.protops.gateway.processor;

import com.protops.gateway.service.PollingService;
import com.protops.gateway.service.weixin.ParkingLotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by fanlin on 16/6/17.
 */
public class PollingParkinglotProcessor implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(PollingService.class);

    private Integer groupCount = 1000; //每个线程负责1000个sensor

    private Integer groupNo;

    private ApplicationContext applicationContext;

    PollingParkinglotProcessor(ApplicationContext applicationContext, Integer tasks)
    {
        this.groupNo = tasks;
        this.applicationContext = applicationContext;
    }

    public void run() {

        logger.debug("[PollingParkinglotProcessor] Polling...");

        PollingService pollingService = applicationContext.getBean(PollingService.class);

        try {
            pollingService.pollingExpired(groupNo, groupCount);
            pollingService.pollingExpiring(groupNo, groupCount);
            pollingService.pollingToExpire(groupNo, groupCount);

        }catch (Exception e){
            e.printStackTrace();
        }

        logger.debug("[PollingParkinglotProcessor] Polling...end");

    }

}
