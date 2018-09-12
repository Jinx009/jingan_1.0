package com.protops.gateway.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by damen on 2016/4/16.
 */
public class StatsResult {

    @JsonIgnore
    private BigDecimal availableCnt;
    @JsonIgnore
    private BigDecimal totalCnt;
    @JsonProperty("利用率")
    private Double percent;
    @JsonProperty("period")
    private String unit;

    private Integer week;

    public BigDecimal getAvailableCnt() {
        return availableCnt;
    }

    public void setAvailableCnt(BigDecimal availableCnt) {
        this.availableCnt = availableCnt;
    }

    public BigDecimal getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(BigDecimal totalCnt) {
        this.totalCnt = totalCnt;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }
}
