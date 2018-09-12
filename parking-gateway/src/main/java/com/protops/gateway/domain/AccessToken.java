package com.protops.gateway.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by damen on 2016/5/17.
 */
public class AccessToken {

    @JsonProperty("accessToken")
    private String token;
    @JsonIgnore
    private AppInfo appInfo;

    public AccessToken(String token, AppInfo appInfo) {
        this.token = token;
        this.appInfo = appInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }
}
