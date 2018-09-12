package com.protops.gateway.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ServiceStaticAccess implements ApplicationContextAware, DisposableBean {
    private static Logger logger = LoggerFactory.getLogger(ServiceStaticAccess.class);

    /**
     * Spring应用上下文
     */
    private static ApplicationContext applicationContext = null;

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.debug("注入ApplicationContext到SpringApplicationContext:" + applicationContext);

        if (ServiceStaticAccess.applicationContext != null) {
            logger.warn("ServiceStaticAccess中的ApplicationContext被覆盖, 原有ApplicationContext为:"
                    + ServiceStaticAccess.applicationContext);
        }

        ServiceStaticAccess.applicationContext = applicationContext; // NOSONAR
    }

    /**
     * 实现DisposableBean接口,在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() throws Exception {
        ServiceStaticAccess.clear();
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class clazz) {
        assertContextInjected();
        return (T) applicationContext.getBean(name, clazz);
    }


    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除ServiceStaticAccess中的ApplicationContext为Null.
     */
    public static void clear() {
        logger.debug("清除ServiceStaticAccess中的ApplicationContext:" + applicationContext);
        applicationContext = null;
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException(
                    "applicaitonContext未注入,请在applicationContext.xml中定义ServiceStaticAccess");
        }
    }
}
