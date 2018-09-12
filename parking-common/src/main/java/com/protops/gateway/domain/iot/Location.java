package com.protops.gateway.domain.iot;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by doubleminter on 2016/4/2.
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "level")
    private Integer level;
    @Column(name = "tag")
    private String tag;
    @Column(name = "description")
    private String desc;
    @Column(name = "rec_st")
    private Integer recSt = 1;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "app_info_id")
    private Integer appInfoId;
    @Column(name = "app_info_desc")
    private String appInfoDesc;

    /**
     * 2017-04-19新增
     */
    @Column(name = "notice_type")
    private Integer noticeType;

}
