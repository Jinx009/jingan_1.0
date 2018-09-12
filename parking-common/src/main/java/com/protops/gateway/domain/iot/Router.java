package com.protops.gateway.domain.iot;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * 接收机设备基础模型
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_router")
public class Router {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="mac")
    private String mac;
    @Column(name="channel_id")
    private String channelId;
    @Column(name="pan_id")
    private String panId;
    @Column(name="heartbeat_interval")
    private String heartbeatInterval;
    @Column(name="interface_version")
    private String interfaceVersion;
    @Column(name="firmware_version")
    private String firmwareVersion;
    @Column(name="router_firmware_version")
    private String routerFirmwareVersion;
    @Column(name="frequency")
    private String frequency;
    @Column(name="status")
    private Integer status;
    @Column(name="operation")
    private Integer operation;
    @Column(name="secret")
    private String secret;
    @Column(name = "last_seen_time")
    private Date lastSeenTime;
    @Column(name="note")
    private String note;
    @Column(name="hardware_version")
    private String hardwareVersion;
    @Column(name="r_hardware_version")
    private String rHardwareVersion;
    /**
     * 不更新数据
     */
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "latitude")
    private String latitude;
    @Column(name="rec_st")
    private Integer recSt = 1;
    @Column(name="create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 2017-03-07新增字段
     */
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "location_id")
    private Integer location_id;


}
