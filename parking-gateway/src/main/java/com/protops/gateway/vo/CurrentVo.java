package com.protops.gateway.vo;

/**
 * Created by damen on 2016/6/18.
 */
public class CurrentVo {

    private String areaName;
    private String lotNo;
    private String date;
    private String startTime;
    private String endTime;
    private String totalAmount;
    private Integer parkTimespan;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getParkTimespan() {
        return parkTimespan;
    }

    public void setParkTimespan(Integer parkTimespan) {
        this.parkTimespan = parkTimespan;
    }
}
