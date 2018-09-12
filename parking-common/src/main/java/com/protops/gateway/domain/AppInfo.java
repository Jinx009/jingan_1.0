package com.protops.gateway.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhouzhihao on 2015/4/1.
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_app_info")
public class AppInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "app_id")
    private String appId;
    @Column(name = "app_secret")
    private String appSecret;
    @Column(name = "rec_st")
    private Integer recSt = 1;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "description")
    private String description;

    /**
     * 2017-04-19新增
     */
    @Column(name = "notice_url")
    private String noticeUrl;
    @Column(name = "path")
    private String path;


}
