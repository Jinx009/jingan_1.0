package com.protops.gateway.domain;

/**
 * Created by zhouzhihao on 2015/5/7.
 */
public abstract class SystemConfig {

    private String filePath;

    private String contextPath;

    private boolean onTestMode = false;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public boolean isOnTestMode() {
        return onTestMode;
    }

    public void setOnTestMode(boolean onTestMode) {
        this.onTestMode = onTestMode;
    }
}
