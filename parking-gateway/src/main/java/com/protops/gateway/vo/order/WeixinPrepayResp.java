package com.protops.gateway.vo.order;

import com.protops.gateway.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by damen on 2015/7/20.
 */
@XStreamAlias("xml")
public class WeixinPrepayResp extends WeixinPaymentMsg {

    @XStreamAlias("appid")
    private String appId;

    @XStreamAlias("mch_id")
    private String mchId;

    @XStreamAlias("nonce_str")
    private String nonceStr;

    @XStreamAlias("trade_type")
    private String tradeType;

    @XStreamAlias("prepay_id")
    private String prepayId;

    @XStreamAlias("code_url")
    private String codeUrl;

    @XStreamAlias("device_info")
    private String deviceInfo;

    public WeixinPrepayResp() {
        super("pushOrder");
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
        if (!StringUtils.isEmpty(appId)) {
            signMap.put("appid", appId);
        }
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
        if (!StringUtils.isEmpty(mchId)) {
            signMap.put("mch_id", mchId);
        }
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
        if (!StringUtils.isEmpty(nonceStr)) {
            signMap.put("nonce_str", nonceStr);
        }
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
        if (!StringUtils.isEmpty(tradeType)) {
            signMap.put("trade_type", tradeType);
        }
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
        if (!StringUtils.isEmpty(prepayId)) {
            signMap.put("prepay_id", prepayId);
        }
    }


    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
        if (!StringUtils.isEmpty(codeUrl)) {
            signMap.put("code_url", codeUrl);
        }
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
        if (!StringUtils.isEmpty(deviceInfo)) {
            signMap.put("device_info", deviceInfo);
        }
    }

    public void forceRefresh() {

        super.forceRefresh();

        setNonceStr(getNonceStr());
        setAppId(getAppId());
        setCodeUrl(getCodeUrl());
        setDeviceInfo(getDeviceInfo());
        setMchId(getMchId());
        setPrepayId(getPrepayId());
        setTradeType(getTradeType());

    }
}
