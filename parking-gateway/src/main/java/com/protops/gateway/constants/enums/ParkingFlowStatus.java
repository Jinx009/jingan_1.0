package com.protops.gateway.constants.enums;

/**
 * Created by damen on 2016/6/14.
 */
public enum ParkingFlowStatus {

    ongoing(1, "正在进行"), ok(2, "已完成");

    private Integer status;
    private String msg;

    ParkingFlowStatus(Integer status, String msg){
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

    public static ParkingFlowStatus parse(Integer code) {
        for (ParkingFlowStatus result : values()) {
            if (result.getStatus().equals(code)) {
                return result;
            }
        }
        return null;
    }

}
