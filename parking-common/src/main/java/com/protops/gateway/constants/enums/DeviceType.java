package com.protops.gateway.constants.enums;

/**
 * Created by damen on 2016/12/1.
 */
public enum DeviceType {

    Router("1"), Repeater("2"), Sensor("3");

    private String i;

    DeviceType(String i) {
        this.i = i;
    }

    public static DeviceType parse(String code) {
        for (DeviceType result : values()) {
            if (result.getI().equals(code)) {
                return result;
            }
        }
        return null;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }
}
