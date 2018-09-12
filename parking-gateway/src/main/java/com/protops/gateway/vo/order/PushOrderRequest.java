package com.protops.gateway.vo.order;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by zhouzhihao on 2015/7/23.
 */
public class PushOrderRequest {

    private String parkingLot;
    private Integer timespan;
    @JsonProperty("isRenewal")
    private boolean isRenewal;

    public String getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Integer getTimespan() {
        return timespan;
    }

    public void setTimespan(Integer timespan) {
        this.timespan = timespan;
    }

    public boolean isRenewal() {
        return isRenewal;
    }

    public void setRenewal(boolean renewal) {
        isRenewal = renewal;
    }
}
