package com.protops.gateway.domain;

import com.protops.gateway.utils.AmountUtil;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by damen on 2016/6/12.
 */
@Entity
@Table(name = "tbl_charge_policy")
public class ChargePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "first_hour")
    private Integer firstHour;

    @Column(name = "remain_half_hour")
    private Integer remainHalfHour;

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

    public Integer getFirstHour() {
        return firstHour;
    }

    public void setFirstHour(Integer firstHour) {
        this.firstHour = firstHour;
    }

    public Integer getRemainHalfHour() {
        return remainHalfHour;
    }

    public void setRemainHalfHour(Integer remainHalfHour) {
        this.remainHalfHour = remainHalfHour;
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

    public String getFirstHourDisplay(){
        return AmountUtil.toString(getFirstHour());
    }

    public String getRemainHalfHourDisplay(){
        return AmountUtil.toString(getRemainHalfHour());

    }
}
