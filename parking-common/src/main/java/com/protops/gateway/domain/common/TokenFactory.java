package com.protops.gateway.domain.common;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jinx on 3/6/17.
 * access_token
 */
@Entity
@Table(name = "tbl_token_factory")
public class TokenFactory {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "base_id")
    private String baseId;//mac地址或者商户appId
    @Column(name = "secret")
    private String secret;//加密秘钥
    @Column(name = "token")
    private String token;//当前token
    @Column(name = "timestamp")
    private Long timestamp;//失效时间戳
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//创建时间
    @Column(name = "invalid_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invalidTime;//失效时间
    @Column(name = "type")
    private Integer type;//加密业务类型0表示设备,1表示商户

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
