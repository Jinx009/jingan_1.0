package com.protops.gateway.constants;

import com.protops.gateway.Job.AreaJob;
import com.protops.gateway.Job.StatsJob;
import com.protops.gateway.domain.AppInfo;
import com.protops.gateway.domain.Config;
import com.protops.gateway.domain.SystemConfig;
import com.protops.gateway.processor.ThreadProcessor;
import com.protops.gateway.service.AuthTokenService;
import com.protops.gateway.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouzhihao on 2014/10/15.
 */
public class AdminConfig extends SystemConfig implements ApplicationListener<ContextRefreshedEvent> {

    private Integer systemId;

    private static Map<String, String> inMemoryConfig = new HashMap<String, String>();

    private static Map<String, AppInfo> appInfoMap = new HashMap<String, AppInfo>();

    private static String staticContextPath;

    private static String staticResourcePath;

    public static String KEY_ACCESS_TOKEN_MAX_COUNT = "access_token_max_count";

    public static String KEY_ACCESS_TOKEN_TIMEOUT = "access_token_timeout";


    @Autowired
    ConfigService configService;

    @Autowired
    SchedulerFactoryBean bean;

    @Autowired
    AreaJob areaJob;

    @Autowired
    StatsJob statsJob;

    @Autowired
    WeixinConfig weixinConfig;

    @Autowired
    AuthTokenService authTokenService;

    @Autowired
    ThreadProcessor threadProcessor;

    public static String getStaticContextPath() {
        return staticContextPath;
    }

    public static void setStaticContextPath(String staticContextPath) {
        AdminConfig.staticContextPath = staticContextPath;
    }

    public static String getStaticResourcePath() {
        return staticResourcePath;
    }

    public static void setStaticResourcePath(String staticResourcePath) {
        AdminConfig.staticResourcePath = staticResourcePath;
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (event.getApplicationContext().getParent() == null) {

            if (event instanceof ContextRefreshedEvent) {

                staticContextPath = getContextPath();

                doLoad();

                authTokenService.initSessionMap();

                areaJob.process();

                statsJob.process();

                threadProcessor.start();

                try {

                    if (!isOnTestMode()) {

                        weixinConfig.reloadAccessToken();

                        weixinConfig.reloadMenu();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                bean.start();

            }

        }

    }

    public void doLoad() {

        List<Config> configs = configService.refresh(systemId);

        Map<String, String> inMemoryConfigTemp = new HashMap<String, String>();
        for (Config config : configs) {

            inMemoryConfigTemp.put(config.getKey(), config.getValue());

        }

        inMemoryConfig = inMemoryConfigTemp;

    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public static String get(String key) {
        return inMemoryConfig.get(key);
    }

    public static Integer getInt(String key) {
        String tmp = inMemoryConfig.get(key);
        if (tmp == null) {
            tmp = "0";
        }
        return Integer.valueOf(tmp);
    }

    public static void setInt(String key, String value) {
        inMemoryConfig.put(key, value);
    }

    public static AppInfo getAppInfo(String appId) {
        return appInfoMap.get(appId);
    }

    public static void refresh(List<Config> configs) {

        Map<String, String> temp = new HashMap<String, String>();

        for (Config config : configs) {

            temp.put(config.getKey(), config.getValue());

        }

        inMemoryConfig = temp;

    }

}
