package com.protops.gateway.constants.enums;

/**
 * Created by damen on 2016/6/13.
 *
 * 1，lost单独一个状态再见
 * 2，unpaid和paid都可以付钱，一个是续费，一个是第一次支付
 *
 */
public enum ParkingLotStatus {

    lost(0), unpaid(1), paid(2), expired(3);

//    lost, available, occupied, unpaid, paid;

    private Integer id;

    ParkingLotStatus(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
