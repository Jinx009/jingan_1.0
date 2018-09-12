package com.protops.gateway.domain;

import java.util.Date;

/**
 * Created by zhouzhihao on 2015/5/12.
 */
public class SmsInfo {

    private String mobile;
    private String smsCode;
    private Integer tryCnt;
    private long lastSuccessSentTime;

    public SmsInfo(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public Integer getTryCnt() {
        return tryCnt;
    }

    public void setTryCnt(Integer tryCnt) {
        this.tryCnt = tryCnt;
    }

    public long getLastSuccessSentTime() {
        return lastSuccessSentTime;
    }

    public void setLastSuccessSentTime(long lastSuccessSentTime) {
        this.lastSuccessSentTime = lastSuccessSentTime;
    }

    public void finishSent(String smsCode) {
        setSmsCode(smsCode);
        setLastSuccessSentTime(new Date().getTime());
        addSendCnt();
    }

    private void addSendCnt() {

        Integer tryCnt = getTryCnt();

        if (tryCnt == null) {
            tryCnt = 0;
        }

        setTryCnt(++tryCnt);
    }
}
