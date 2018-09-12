package com.protops.gateway.weixin.vo;

/**
 *
 * @author zzh
 */
public enum EventPushType {
    SUBSCRIBE("subscribe"), UNSUBSCRIBE("unsubscribe"), CLICK("CLICK");

    private String type;

    private EventPushType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
