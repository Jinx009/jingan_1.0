package com.protops.gateway.vo.order;

import com.protops.gateway.constants.WeixinConfig;
import com.protops.gateway.domain.Order;
import com.protops.gateway.util.NumberGenerator;
import com.protops.gateway.util.StringUtils;
import com.protops.gateway.util.WeixinSignUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by zhouzhihao on 2015/7/23.
 */
@XStreamAlias("xml")
public class OrderQueryRequest {

    @XStreamOmitField
    private TreeMap<String, String> signMap = new TreeMap<String, String>();

    private String appid;

    private String mch_id;

    private String nonce_str;

    private String transaction_id;

    private String out_trade_no;

    private String sign;


    public OrderQueryRequest(Order order, WeixinConfig weixinConfig) {

        setAppid(weixinConfig.getAppId());
        setMch_id(weixinConfig.getMerchantId());
        setNonce_str(NumberGenerator.generate());
        setTransaction_id(order.getTransId());
        setOut_trade_no(order.getOrderNumber());

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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
        if (!StringUtils.isEmpty(appid)) {
            signMap.put("appid", appid);
        }
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
        if (!StringUtils.isEmpty(mch_id)) {
            signMap.put("mch_id", mch_id);
        }
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
        if (!StringUtils.isEmpty(transaction_id)) {
            signMap.put("transaction_id", transaction_id);
        }
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
        if (!StringUtils.isEmpty(out_trade_no)) {
            signMap.put("out_trade_no", out_trade_no);
        }
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
        if (!StringUtils.isEmpty(nonce_str)) {
            signMap.put("nonce_str", nonce_str);
        }
    }
}
