package com.protops.gateway.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fanlin on 16/6/16.
 */

@Entity
@Table(name = "tbl_notice_expiring")
public class NoticeExpiring {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "sensor_id")
    private Integer sensorId;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "expire_time")
    private Date expireTime;

    @Column(name = "sent")
    private Integer sent;

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

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSent() {
        return sent;
    }

    public void setSent(Integer sent) {
        this.sent = sent;
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

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
