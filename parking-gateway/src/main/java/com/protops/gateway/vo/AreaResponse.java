package com.protops.gateway.vo;

import java.util.List;

/**
 * Created by damen on 2016/5/29.
 */
public class AreaResponse {

    private List<AreaDetail> sensors;

    private Integer totalCnt;

    private Integer availableCnt;


    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    public Integer getAvailableCnt() {
        return availableCnt;
    }

    public void setAvailableCnt(Integer availableCnt) {
        this.availableCnt = availableCnt;
    }

    public List<AreaDetail> getSensors() {
        return sensors;
    }

    public void setSensors(List<AreaDetail> sensors) {
        this.sensors = sensors;
    }
}
