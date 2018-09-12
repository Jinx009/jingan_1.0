package com.protops.gateway.constants.enums;

/**
 * Created by damen on 2016/6/14.
 */
public enum OrderStatus {

    unpaid(1, "未支付"), paid(2, "已支付"), refunding(3, "退款中"), refunded(4, "已退款");

    private Integer status;
    private String msg;

    OrderStatus(Integer status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static OrderStatus parse(Integer code) {
        for (OrderStatus result : values()) {
            if (result.getStatus().equals(code)) {
                return result;
            }
        }
        return null;
    }

}
