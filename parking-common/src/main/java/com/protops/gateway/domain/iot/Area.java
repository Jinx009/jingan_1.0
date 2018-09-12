package com.protops.gateway.domain.iot;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by doubleminter on 2016/4/2.
 */
@Entity
@Table(name = "tbl_area")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "level")
    private Integer level;

    @Column(name = "tag")
    private String tag;

    @Column(name = "description")
    private String desc;

    @Column(name = "charge_policy_id")
    private Integer chargePolicyId;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "rec_st")
    private Integer recSt = 1;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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
