package com.protops.gateway.vo.lbs;

import com.protops.gateway.domain.AreaVo;
import com.protops.gateway.domain.ChargePolicy;

/**
 * Created by damen on 2016/6/12.
 */
public class H5AreaDetail extends AreaVo {

    private ChargePolicy chargePolicy;

    public H5AreaDetail(AreaVo areaVo, ChargePolicy chargePolicy) {
        setName(areaVo.getName());
        setDesc(areaVo.getDesc());
        setAvailableCnt(areaVo.getAvailableCnt());
        setTotalCnt(areaVo.getTotalCnt());
        setChargePolicy(chargePolicy);
    }

    public ChargePolicy getChargePolicy() {
        return chargePolicy;
    }

    public void setChargePolicy(ChargePolicy chargePolicy) {
        this.chargePolicy = chargePolicy;
    }
}
