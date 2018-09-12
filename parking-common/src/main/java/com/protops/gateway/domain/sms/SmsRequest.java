package com.protops.gateway.domain.sms;

/**
 * Created by damen on 2015/5/19.
 */
public class SmsRequest {

    private String apikey;
    private String text;
    private String mobile;

    public SmsRequest(String apikey) {
        this.apikey = apikey;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
