package com.protops.gateway.domain;

import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.vo.AreaCnt;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.math.BigInteger;

/**
 * Created by damen on 2016/4/10.
 */
public class AreaVo {

    private Integer id;

    private String name;

    private String desc;

    private BigInteger availableCnt;

    private BigInteger totalCnt;

    private String longitude;

    private String latitude;

    @JsonIgnore
    private Integer chargePolicyId;

    public AreaVo(){}

    public AreaVo(Area area) {
        this.id = area.getId();
        this.name = area.getName();
        this.desc = area.getDesc();
        this.availableCnt = new BigInteger("0");
        this.totalCnt = new BigInteger("0");
        this.chargePolicyId = area.getChargePolicyId();
        this.longitude = area.getLongitude();
        this.latitude = area.getLatitude();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigInteger getAvailableCnt() {
        return availableCnt;
    }

    public void setAvailableCnt(BigInteger availableCnt) {
        this.availableCnt = availableCnt;
    }

    public BigInteger getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(BigInteger totalCnt) {
        this.totalCnt = totalCnt;
    }

    public Integer getChargePolicyId() {
        return chargePolicyId;
    }

    public void setChargePolicyId(Integer chargePolicyId) {
        this.chargePolicyId = chargePolicyId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
