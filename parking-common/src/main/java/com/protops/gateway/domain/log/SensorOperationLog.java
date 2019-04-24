package com.protops.gateway.domain.log;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jinx on 3/24/17.
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_sensor_operationlog")
public class SensorOperationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "available")
    private Integer available;
    @Column(name = "change_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeTime;
    @Column(name = "area_id")
    private Integer areaId;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "description")
    private String description;
    @Column(name = "mac")
    private String mac;


    /**
     * 2017-04-17添加每次车进车出添加流水id
     */
    @Column(name = "log_id")
    private String logId;//单次车进车出同一id
    @Column(name = "send_status")
    private Integer sendStatus;//发送状态0表示未发送 1表示发送成功
    @Column(name = "fail_times")
    private Integer failTimes;//失败次数
    @Column(name = "send_time")
    private Date sendTime;//下次发送时间，三次失败之后

    /**
     * 2019-04-23
     */
    @Column(name = "type")
    private Integer type;//数据来源1表示摄像头null表示地磁
    @Column(name = "status")
    private String status;
    @Column(name = "cameraId")
    private String cameraId;
    @Column(name = "cph")
    private String cph;
    @Column(name = "cp_color")
    private String cpColor;
    @Column(name = "pic_link")
    private String picLink;

}
