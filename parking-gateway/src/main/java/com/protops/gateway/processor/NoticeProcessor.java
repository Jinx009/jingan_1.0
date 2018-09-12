package com.protops.gateway.processor;

import com.protops.gateway.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Created by fanlin on 16/6/17.
 */
public class NoticeProcessor implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(NoticeProcessor.class);

    private ApplicationContext applicationContext;

    NoticeProcessor(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    public void run() {

        logger.debug("Notice...");

        try {
            NoticeService noticeService = applicationContext.getBean(NoticeService.class);

            noticeService.noticeToExpire();
            noticeService.noticeExpiring();
            noticeService.noticeExpired();
        }catch (Exception e){

            e.printStackTrace();
        }
        logger.debug("Notice...end");
    }
}
