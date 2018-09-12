package com.protops.gateway.vo.order;

import com.protops.gateway.domain.ChargePolicy;
import com.protops.gateway.utils.AmountUtil;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by damen on 2016/6/20.
 */
public class ChargePolicyVo {

    private Integer id;

    private String startTime;

    private String endTime;

    private String firstHour;

    private String remainHalfHour;

    public ChargePolicyVo(ChargePolicy chargePolicy) {
        this.id = chargePolicy.getId();
        this.startTime = chargePolicy.getStartTime();
        this.endTime = chargePolicy.getEndTime();
        this.firstHour = AmountUtil.toString(chargePolicy.getFirstHour());
        this.remainHalfHour = AmountUtil.toString(chargePolicy.getRemainHalfHour());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFirstHour() {
        return firstHour;
    }

    public void setFirstHour(String firstHour) {
        this.firstHour = firstHour;
    }

    public String getRemainHalfHour() {
        return remainHalfHour;
    }

    public void setRemainHalfHour(String remainHalfHour) {
        this.remainHalfHour = remainHalfHour;
    }
}
