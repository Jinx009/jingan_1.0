package com.protops.gateway.domain.base;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by zhouzhihao on 2014/10/21.
 */
public class BaseMessage {

    @JsonProperty("version")
    private String version;

    @JsonProperty("flowNo")
    private String flowNo;

    @JsonProperty("sign")
    private String sign;

    @JsonProperty("appId")
    private String appId;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
