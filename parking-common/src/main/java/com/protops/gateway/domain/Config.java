package com.protops.gateway.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhouzhihao on 2014/10/15.
 */

@Entity
@Table(name = "tbl_config")
public class Config{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "config_key")
    private String key;

    @Column(name = "config_value")
    private String value;

    @Column(name = "system_id")
    private Integer systemId;

    @Column(name = "config_group")
    private Integer group;

    @Column(name = "config_desc")
    private Integer configDesc;

    @Column(name="rec_st")
    private Integer recSt = 1;

    @Column(name="create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getConfigDesc() {
        return configDesc;
    }

    public void setConfigDesc(Integer configDesc) {
        this.configDesc = configDesc;
    }
}
