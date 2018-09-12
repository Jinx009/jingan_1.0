package com.protops.gateway.domain;

/**
 * Created by zhouzhihao on 2015/7/30.
 */
public class OrderAttach extends Attach{

    private String parkingLot;
    private Integer areaId;

    public String getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getAreaId() {
        return areaId;
    }
}
