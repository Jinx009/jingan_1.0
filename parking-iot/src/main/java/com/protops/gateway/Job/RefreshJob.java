package com.protops.gateway.Job;

import com.protops.gateway.constants.AdminConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhouzhihao on 2014/11/4.
 */
public class RefreshJob {

    private static final Logger logger = LoggerFactory.getLogger(RefreshJob.class);

    @Autowired
    AdminConfig adminConfig;

    private boolean onTestMode;

    public void refreshAll() {

        adminConfig.doLoad();

    }

    public void setOnTestMode(boolean onTestMode) {
        this.onTestMode = onTestMode;
    }
}
