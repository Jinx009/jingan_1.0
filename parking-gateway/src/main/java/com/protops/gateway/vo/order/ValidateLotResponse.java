package com.protops.gateway.vo.order;

import com.protops.gateway.domain.ChargePolicy;

/**
 * Created by zhouzhihao on 2015/7/23.
 */
public class ValidateLotResponse {

    private ChargePolicyVo chargePolicy;

    private String parkingLot;

    public ChargePolicyVo getChargePolicy() {
        return chargePolicy;
    }

    public void setChargePolicy(ChargePolicyVo chargePolicy) {
        this.chargePolicy = chargePolicy;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }
}
