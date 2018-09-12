package com.protops.gateway.utils;


import com.protops.gateway.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

/**
 * Created by jinx on 3/13/17.
 */

public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context = null;
    private static SpringContextUtil stools = null;
    public synchronized static SpringContextUtil init(){
        if(stools == null){
            stools = new SpringContextUtil();
        }
        return stools;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }
    public synchronized static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

}

