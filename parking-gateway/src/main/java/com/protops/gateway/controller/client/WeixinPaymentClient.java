package com.protops.gateway.controller.client;

import com.protops.gateway.constants.WeixinConfig;
import com.protops.gateway.domain.Order;
import com.protops.gateway.util.HttpUtil;
import com.protops.gateway.util.StringUtils;
import com.protops.gateway.util.XMLParser;
import com.protops.gateway.vo.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by damen on 2015/7/20.
 */
public class WeixinPaymentClient {

    private static final Logger logger = LoggerFactory.getLogger(WeixinPaymentClient.class);

    private String prepayUrl;

    private String notifyUrl;

    private String queryUrl;


    public OrderQueryResponse query(Order order, WeixinConfig weixinConfig) throws Exception {

        OrderQueryRequest orderQueryRequest = new OrderQueryRequest(order, weixinConfig);

        orderQueryRequest.sign(weixinConfig);

        OrderQueryResponse response = (OrderQueryResponse) doSend(getQueryUrl(), orderQueryRequest, OrderQueryResponse.class);

        response.forceRefresh();

        if (!responseOK(response, weixinConfig)) {
            return null;
        }

        return response;

    }


    public NotifyRequest notify(String notifyRequestStr, WeixinConfig weixinConfig) {

        logger.warn("[notify][rawNotify][{}]", notifyRequestStr);

        NotifyRequest notifyRequest = (NotifyRequest) XMLParser.fromXML(notifyRequestStr, NotifyRequest.class);

        notifyRequest.forceRefresh();

        if (!responseOK(notifyRequest, weixinConfig)) {
            return null;
        }

        return notifyRequest;

    }

    public String pushOrder(String openId, Order order, WeixinConfig weixinConfig) throws Exception {

        try {

            WeixinPrepayReq weixinPrepayReq = new WeixinPrepayReq(openId, weixinConfig);
//        WeixinPrepayReq weixinPrepayReq = new WeixinPrepayReq("oGwLzjk2whkKDuXB7ioxxSm_WjTU", weixinConfig);

            weixinPrepayReq.parse(order);

            weixinPrepayReq.setNotifyUrl(getNotifyUrl());

            weixinPrepayReq.sign(weixinConfig);

            WeixinPrepayResp weixinPrepayResp = (WeixinPrepayResp) doSend(getPrepayUrl(), weixinPrepayReq, WeixinPrepayResp.class);

            // fromXML之后的对象不会执行set方法，强制刷新，并建立signMap
            weixinPrepayResp.forceRefresh();

            if (!responseOK(weixinPrepayResp, weixinConfig)) {
                return null;
            }

            return weixinPrepayResp.getPrepayId();

        } catch (Exception e) {

            logger.warn("pushOrder", e);
            return null;

        }
    }

    public Object doSend(String url, Object obj, Class clazz) throws Exception {

        String postDataXML = XMLParser.toXML(obj);

        Map<String, String> headers = new HashMap<String, String>();

        headers.put("Content-Type", "text-html");

        logger.warn("[rawRequest][{}]", postDataXML);

        String response = HttpUtil.post(url, postDataXML, headers);

        logger.warn("[rawResponse][{}]", response);

        return XMLParser.fromXML(response, clazz);

    }

    private boolean responseOK(WeixinPaymentMsg weixinPaymentMsg, WeixinConfig weixinConfig) {

        String sign = weixinPaymentMsg.getSign(weixinConfig);

        if (StringUtils.isEmpty(sign)) {
            logger.warn("[{}][signEmpty][{}]", new Object[]{weixinPaymentMsg.getName(), sign});
            return false;
        }

        if (!sign.equals(weixinPaymentMsg.getSign())) {
            logger.warn("[{}][signFailed][response={}][calc={}]", new Object[]{weixinPaymentMsg.getName(), weixinPaymentMsg.getSign(), sign});
            return false;
        }

        // 失败处理
        if (weixinPaymentMsg.getReturn_code().equals("FAIL")) {
            logger.warn("[{}][{}]", new Object[]{weixinPaymentMsg.getName(), weixinPaymentMsg.getReturn_msg()});
            return false;
        }

        if (weixinPaymentMsg.getResult_code().equals("FAIL")) {
            logger.warn("[{}][{}][{}]", new Object[]{weixinPaymentMsg.getName(), weixinPaymentMsg.getErr_code(), weixinPaymentMsg.getErr_code_des()});
            return false;
        }

        return true;

    }


    public String getPrepayUrl() {
        return prepayUrl;
    }

    public void setPrepayUrl(String prepayUrl) {
        this.prepayUrl = prepayUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }
}
