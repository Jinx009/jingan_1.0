package com.protops.gateway.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by damen on 2016/6/14.
 */
@Entity
@Table(name = "tbl_parking_flow")
public class ParkingFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "parking_time")
    private Date parkingTime;

    @Column(name = "area_id")
    private Integer areaId;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "parking_timespan")
    private Integer parkingTimespam;

    @Column(name = "status")
    private Integer status = 2;

    @Column(name = "rec_st")
    private int recSt = 1;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(Date parkingTime) {
        this.parkingTime = parkingTime;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public int getRecSt() {
        return recSt;
    }

    public void setRecSt(int recSt) {
        this.recSt = recSt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Integer getParkingTimespam() {
        return parkingTimespam;
    }

    public void setParkingTimespam(Integer parkingTimespam) {
        this.parkingTimespam = parkingTimespam;
    }
}
