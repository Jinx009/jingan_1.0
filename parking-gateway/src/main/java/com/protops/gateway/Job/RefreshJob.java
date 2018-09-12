package com.protops.gateway.Job;

import com.protops.gateway.constants.AdminConfig;
import com.protops.gateway.constants.WeixinConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhouzhihao on 2014/11/4.
 */
public class RefreshJob {

    private static final Logger logger = LoggerFactory.getLogger(RefreshJob.class);

    @Autowired
    WeixinConfig weixinConfig;

    @Autowired
    AdminConfig adminConfig;

    private boolean onTestMode;

    public void refreshAll() {

        try {
            if (!onTestMode) {
                weixinConfig.reloadAccessToken();
            }
        } catch (Exception e) {
            logger.warn("accessTokenRefreshFailed");
        }

        try {

            weixinConfig.loadFromFile();

            if (!onTestMode) {
                weixinConfig.reloadMenu();
            }
        } catch (Exception e) {
            logger.warn("reloadMenuFailed");
        }

        adminConfig.doLoad();

    }

    public void setOnTestMode(boolean onTestMode) {
        this.onTestMode = onTestMode;
    }
}
