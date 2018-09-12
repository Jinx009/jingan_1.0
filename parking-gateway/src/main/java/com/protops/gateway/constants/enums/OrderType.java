package com.protops.gateway.constants.enums;

/**
 * Created by damen on 2016/6/14.
 */
public enum OrderType {

    charging(1, "充值"), payment(2, "缴费"), xufei(3, "续费");

    private Integer status;
    private String msg;

    OrderType(Integer status, String msg){
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

    public static OrderType parse(Integer code) {
        for (OrderType result : values()) {
            if (result.getStatus().equals(code)) {
                return result;
            }
        }
        return null;
    }

}
