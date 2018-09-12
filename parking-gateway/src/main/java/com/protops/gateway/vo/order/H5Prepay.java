package com.protops.gateway.vo.order;

import com.protops.gateway.util.NumberGenerator;
import com.protops.gateway.util.StringUtils;
import com.protops.gateway.util.WeixinSignUtil;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by zhouzhihao on 2015/7/23.
 */
public class H5Prepay {

    @JsonIgnore
    private TreeMap<String, String> signMap = new TreeMap<String, String>();

    private String appId;

    private String timeStamp;

    private String nonceStr;

    @JsonProperty("package")
    private String packageStr;

    private String signType;

    private String paySign;

    public H5Prepay(String appId, String prepayId) {

        setAppId(appId);

        setPackageStr("prepay_id=" + prepayId);

        setSignType("MD5");

        setTimeStamp(String.valueOf(new Date().getTime()));

        setNonceStr(NumberGenerator.generate());

    }

    public void sign(String key) {

        StringBuilder sb = new StringBuilder();

        Set<Map.Entry<String,String>> entrySet = signMap.entrySet();

        for(Map.Entry<String, String> entry : entrySet){

            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        sb.append("key=").append(key);

        String sign = WeixinSignUtil.MD5Encode(sb.toString()).toUpperCase();

        setPaySign(sign);

    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
        if (!StringUtils.isEmpty(appId)) {
            signMap.put("appId", appId);
        }
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
        if (!StringUtils.isEmpty(timeStamp)) {
            signMap.put("timeStamp", timeStamp);
        }
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
        if (!StringUtils.isEmpty(nonceStr)) {
            signMap.put("nonceStr", nonceStr);
        }
    }

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
        if (!StringUtils.isEmpty(packageStr)) {
            signMap.put("package", packageStr);
        }
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
        if (!StringUtils.isEmpty(signType)) {
            signMap.put("signType", signType);
        }
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
        if (!StringUtils.isEmpty(paySign)) {
            signMap.put("paySign", paySign);
        }
    }
}
