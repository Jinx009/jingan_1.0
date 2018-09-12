package com.protops.gateway.weixin.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zzh
 */
public class Signature {
    private String token = "zhanway2016";
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;

    public Signature(HttpServletRequest request) {

        this.signature = request.getParameter("signature");
        this.timestamp = request.getParameter("timestamp");
        this.nonce = request.getParameter("nonce");
        this.echostr = request.getParameter("echostr");

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

}
