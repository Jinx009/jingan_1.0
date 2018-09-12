package com.protops.gateway.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by damen on 2016/4/16.
 */
@Entity
@Table(name = "tbl_stats_sample")
public class StatsSample {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="available_cnt")
    private Integer availableCnt;

    @Column(name="total_cnt")
    private Integer totalCnt;

    @Column(name="percent")
    private Integer percent;

    @Column(name="year")
    private Integer year;

    @Column(name="month")
    private Integer month;

    @Column(name="week")
    private Integer week;

    @Column(name="day")
    private Integer day;

    @Column(name="hour")
    private Integer hour;

    @Column(name="area_id")
    private Integer areaId;

    @Column(name="log_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logTime;

    @Column(name="rec_st")
    private Integer recSt = 1;

    @Column(name="create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAvailableCnt() {
        return availableCnt;
    }

    public void setAvailableCnt(Integer availableCnt) {
        this.availableCnt = availableCnt;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Integer getRecSt() {
        return recSt;
    }

    public void setRecSt(Integer recSt) {
        this.recSt = recSt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }
}
