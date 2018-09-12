package com.protops.gateway.vo.order;

import com.protops.gateway.constants.WeixinConfig;
import com.protops.gateway.domain.Order;
import com.protops.gateway.util.DateUtil;
import com.protops.gateway.util.StringUtils;
import com.protops.gateway.util.WeixinSignUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.*;

/**
 * Created by damen on 2015/7/20.
 */
@XStreamAlias("xml")
public class WeixinPrepayReq {

    public static final String TRADE_TYPE_JSAPI = "JSAPI";

    public static final String DEVICE_INFO_WEB = "WEB";

    @XStreamOmitField
    private TreeMap<String, String> signMap = new TreeMap<String, String>();


    @XStreamAlias("openid")
    private String openId;

    @XStreamAlias("appid")
    private String appId;

    @XStreamAlias("mch_id")
    private String mchId;

    @XStreamAlias("nonce_str")
    private String nonceStr;

    @XStreamAlias("sign")
    private String sign;

    @XStreamAlias("body")
    private String body;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @XStreamAlias("total_fee")
    private String totalFee;

    @XStreamAlias("fee_type")
    private String feeType;

    @XStreamAlias("spbill_create_ip")
    private String spBillCreateIp;

    @XStreamAlias("time_start")
    private String timeStart;

    @XStreamAlias("time_expire")
    private String timeExpire;

    @XStreamAlias("notify_url")
    private String notifyUrl;

    @XStreamAlias("trade_type")
    private String tradeType;

    @XStreamAlias("goods_tag")
    private String goodsTag;

    @XStreamAlias("device_info")
    private String deviceInfo;

    @XStreamAlias("attach")
    private String attach;

    public WeixinPrepayReq(String openId, WeixinConfig weixinConfig) {
        setMchId(weixinConfig.getMerchantId());
        setAppId(weixinConfig.getAppId());
        setOpenId(openId);
        setTradeType(TRADE_TYPE_JSAPI);
        setDeviceInfo(DEVICE_INFO_WEB);
        setFeeType(weixinConfig.getCurrencyCode());
    }

    public void parse(Order order) {
        setTotalFee(order.getTotalAmount() + "");
        setNonceStr(order.getOrderNumber());
        setOutTradeNo(order.getOrderNumber());

        Date timeStartDate = new Date();

        String timeStart = DateUtil.getDate(timeStartDate, DateUtil.DATE_FMT_YMDHMS);

        Date timeExpireDate = DateUtil.getAddedTime(timeStartDate, Calendar.MINUTE, 30);

        String timeExpire = DateUtil.getDate(timeExpireDate, DateUtil.DATE_FMT_YMDHMS);

        setTimeStart(timeStart);
        setTimeExpire(timeExpire);

        setBody("parkingFee");

        setSpBillCreateIp("127.0.0.1");

        setAttach(order.getAttach().toBase64());

    }

    public void sign(WeixinConfig weixinConfig) {

        StringBuilder sb = new StringBuilder();

        Set<Map.Entry<String, String>> entrySet = signMap.entrySet();

        for (Map.Entry<String, String> entry : entrySet) {

            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        sb.append("key=").append(weixinConfig.getKey());

        String sign = WeixinSignUtil.MD5Encode(sb.toString()).toUpperCase();

        setSign(sign);

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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
        if (!StringUtils.isEmpty(body)) {
            signMap.put("body", body);
        }
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        if (!StringUtils.isEmpty(outTradeNo)) {
            signMap.put("out_trade_no", outTradeNo);
        }
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
        if (!StringUtils.isEmpty(totalFee)) {
            signMap.put("total_fee", totalFee);
        }
    }

    public String getSpBillCreateIp() {
        return spBillCreateIp;
    }

    public void setSpBillCreateIp(String spBillCreateIp) {
        this.spBillCreateIp = spBillCreateIp;
        if (!StringUtils.isEmpty(spBillCreateIp)) {
            signMap.put("spbill_create_ip", spBillCreateIp);
        }
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
        if (!StringUtils.isEmpty(timeStart)) {
            signMap.put("time_start", timeStart);
        }
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
        if (!StringUtils.isEmpty(timeExpire)) {
            signMap.put("time_expire", timeExpire);
        }
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        if (!StringUtils.isEmpty(notifyUrl)) {
            signMap.put("notify_url", notifyUrl);
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

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
        if (!StringUtils.isEmpty(goodsTag)) {
            signMap.put("goods_tag", goodsTag);
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

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
        if (!StringUtils.isEmpty(attach)) {
            signMap.put("attach", attach);
        }
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
        if (!StringUtils.isEmpty(openId)) {
            signMap.put("openid", openId);
        }
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
        if (!StringUtils.isEmpty(feeType)) {
            signMap.put("fee_type", feeType);
        }
    }
}
