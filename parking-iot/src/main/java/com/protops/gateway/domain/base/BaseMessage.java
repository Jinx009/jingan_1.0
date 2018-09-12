package com.protops.gateway.domain.base;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by zhouzhihao on 2014/10/21.
 */
public class BaseMessage {

    @JsonProperty("m")
    private String mac;

    @JsonProperty("s")
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
