package com.protops.gateway.vo.order;

import com.protops.gateway.constants.WeixinConfig;
import com.protops.gateway.util.StringUtils;
import com.protops.gateway.util.WeixinSignUtil;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by zhouzhihao on 2015/7/29.
 */
public class WeixinPaymentMsg {

    @XStreamOmitField
    private String name;

    public WeixinPaymentMsg(String name){
        this.name = name;
    }

    protected TreeMap<String, String> signMap = new TreeMap<String, String>();

    private String sign;
    private String result_code;
    private String return_code;
    private String return_msg;
    private String err_code;
    private String err_code_des;

    public String getSign(WeixinConfig weixinConfig) {

        StringBuilder sb = new StringBuilder();

        Set<Map.Entry<String, String>> entrySet = signMap.entrySet();

        for (Map.Entry<String, String> entry : entrySet) {

            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        sb.append("key=").append(weixinConfig.getKey());

        return WeixinSignUtil.MD5Encode(sb.toString()).toUpperCase();

    }

    public void forceRefresh(){

        signMap = new TreeMap<String, String>();

        setReturn_code(getReturn_code());
        setReturn_msg(getReturn_msg());
        setErr_code(getErr_code());
        setErr_code_des(getErr_code_des());
        setResult_code(getResult_code());
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
        if (!StringUtils.isEmpty(return_code)) {
            signMap.put("return_code", return_code);
        }
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
        if (!StringUtils.isEmpty(result_code)) {
            signMap.put("result_code", result_code);
        }
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
        if (!StringUtils.isEmpty(return_msg)) {
            signMap.put("return_msg", return_msg);
        }
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
        if (!StringUtils.isEmpty(err_code)) {
            signMap.put("err_code", err_code);
        }
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
        if (!StringUtils.isEmpty(err_code_des)) {
            signMap.put("err_code_des", err_code_des);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
