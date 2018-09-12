package com.protops.gateway.vo.order;

import com.protops.gateway.util.StringUtils;

/**
 * Created by zhouzhihao on 2015/7/24.
 */
public class NotifyRequest extends WeixinPaymentMsg {

    private String appid;
    private String attach;
    private String bank_type;
    private String fee_type;
    private String is_subscribe;
    private String mch_id;
    private String nonce_str;
    private String openid;
    private String out_trade_no;
    private String sub_mch_id;
    private String time_end;
    private String total_fee;
    private String trade_type;
    private String transaction_id;
    private String cash_fee;
    private String device_info;

    public NotifyRequest() {
        super("notify");
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

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
        if (!StringUtils.isEmpty(attach)) {
            signMap.put("attach", attach);
        }
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
        if (!StringUtils.isEmpty(fee_type)) {
            signMap.put("fee_type", fee_type);
        }
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
        if (!StringUtils.isEmpty(is_subscribe)) {
            signMap.put("is_subscribe", is_subscribe);
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

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
        if (!StringUtils.isEmpty(nonce_str)) {
            signMap.put("nonce_str", nonce_str);
        }
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
        if (!StringUtils.isEmpty(openid)) {
            signMap.put("openid", openid);
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

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
        if (!StringUtils.isEmpty(sub_mch_id)) {
            signMap.put("sub_mch_id", sub_mch_id);
        }
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
        if (!StringUtils.isEmpty(time_end)) {
            signMap.put("time_end", time_end);
        }
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
        if (!StringUtils.isEmpty(total_fee)) {
            signMap.put("total_fee", total_fee);
        }
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
        if (!StringUtils.isEmpty(trade_type)) {
            signMap.put("trade_type", trade_type);
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

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
        if (!StringUtils.isEmpty(bank_type)) {
            signMap.put("bank_type", bank_type);
        }
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
        if (!StringUtils.isEmpty(cash_fee)) {
            signMap.put("cash_fee", cash_fee);
        }
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
        if (!StringUtils.isEmpty(device_info)) {
            signMap.put("device_info", device_info);
        }
    }


    public void forceRefresh() {

        super.forceRefresh();

        setAppid(getAppid());
        setAttach(getAttach());
        setBank_type(getBank_type());
        setFee_type(getFee_type());
        setIs_subscribe(getIs_subscribe());
        setMch_id(getMch_id());
        setNonce_str(getNonce_str());
        setOpenid(getOpenid());
        setOut_trade_no(getOut_trade_no());

        setSub_mch_id(getSub_mch_id());
        setTime_end(getTime_end());
        setTotal_fee(getTotal_fee());
        setTransaction_id(getTransaction_id());
        setTrade_type(getTrade_type());

        setCash_fee(getCash_fee());
        setDevice_info(getDevice_info());

    }
}
