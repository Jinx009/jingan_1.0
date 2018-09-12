package com.protops.gateway.domain.log;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jinx on 3/13/17.
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_router_devicelog")
public class RouterDeviceLog {

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
     * 不需要前端传的数据
     */
    @Column(name = "create_time")
    private Date createTime;
}
