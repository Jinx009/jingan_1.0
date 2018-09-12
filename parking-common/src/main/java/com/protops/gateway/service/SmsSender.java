package com.protops.gateway.service;

import com.protops.gateway.domain.sms.Result;
import com.protops.gateway.domain.sms.SmsRequest;
import com.protops.gateway.domain.sms.SmsResponse;
import com.protops.gateway.util.HttpUtil;
import com.protops.gateway.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouzhihao on 2015/5/12.
 */
public class SmsSender {

    private static final Logger logger = LoggerFactory.getLogger(SmsSender.class);

    private String apiKey;

    private String url;

    public boolean send(String mobile, String msg) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", getApiKey());
        params.put("text", msg);
        params.put("mobile", mobile);
        String ret = null;

        try {
            ret = HttpUtil.post(getUrl(), params);

            logger.warn("[smsResponse][{}]", ret);

        } catch (Exception e) {
            logger.warn("send to smsProvider failed", e);
            return false;
        }

        SmsResponse smsResponse = JsonUtil.fromJson(ret, SmsResponse.class);

        if (smsResponse == null) {
            return false;
        }

        if (smsResponse.getCode() != 0) {
            return false;
        }

        Result result = smsResponse.getResult();

        if (result == null) {
            return false;
        }

        return true;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
