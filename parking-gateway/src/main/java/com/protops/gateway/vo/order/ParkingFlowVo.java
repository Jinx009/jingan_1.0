package com.protops.gateway.vo.order;

import com.protops.gateway.constants.enums.ParkingFlowStatus;

import java.util.Date;

/**
 * Created by damen on 2016/6/15.
 */
public class ParkingFlowVo {

    private Integer id;

    private String areaName;

    private String lotNo;

    private String day;

    private String date;

    private Integer status;

    private Date parkingTime;

    private Integer parkingTimespan;

    public String getParkingStatusDisplay(){
        return ParkingFlowStatus.parse(getStatus()).getMsg();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(Date parkingTime) {
        this.parkingTime = parkingTime;
    }

    public Integer getParkingTimespan() {
        return parkingTimespan;
    }

    public void setParkingTimespan(Integer parkingTimespan) {
        this.parkingTimespan = parkingTimespan;
    }
}
