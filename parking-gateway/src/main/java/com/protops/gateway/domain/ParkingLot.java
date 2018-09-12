package com.protops.gateway.domain;

import com.protops.gateway.constants.enums.ParkingLotStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by damen on 2016/6/13.
 * <p>
 * 表示一个业务抽象，其实是sensor的业务表示。
 */
@Entity
@Table(name = "tbl_sensor")
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "connected")
    private Integer connected;

    @Column(name = "available")
    private Integer available;

    @Column(name = "paid")
    private Integer paid;

    @Column(name = "area_id")
    private Integer areaId;

    @Column(name = "in_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inTime;

    @Column(name = "expire_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;

    @Column(name = "notice_flag")
    private Integer noticeFlag;

    @Column(name = "current_member_id")
    private Integer currentMemberId;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "rec_st")
    private int recSt = 1;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Integer getConnected() {
        return connected;
    }

    public void setConnected(Integer connected) {
        this.connected = connected;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public boolean allowPay() {

        if (connected == 0) {
            return false;
        }

        // 1表示有车
        if (available == 1) {

            if(paid == ParkingLotStatus.expired.getId()){
                return false;
            }


            return true;

        } else {

            return false;

        }

    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getNoticeFlag() {
        return noticeFlag;
    }

    public void setNoticeFlag(Integer noticeFlag) {
        this.noticeFlag = noticeFlag;
    }

    public Integer getCurrentMemberId() {
        return currentMemberId;
    }

    public void setCurrentMemberId(Integer currentMemberId) {
        this.currentMemberId = currentMemberId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
