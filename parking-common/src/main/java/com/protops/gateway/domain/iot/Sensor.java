package com.protops.gateway.domain.iot;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by damen on 2016/3/29.
 * 新增字段2017-03-07
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_sensor")
public class Sensor {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "mac")
    private String mac;
    @Column(name = "uid")
    private String uid;
    @Column(name = "description")
    private String desc;
    @Column(name = "firmware_version")
    private String firmwareVersion;
    @Column(name = "battery_voltage")
    private String batteryVoltage;
    @Column(name = "x_mag")
    private String xMag;
    @Column(name = "y_mag")
    private String yMag;
    @Column(name = "z_mag")
    private String zMag;
    @Column(name = "available")
    private Integer available;
    @Column(name = "base_energy")
    private String baseEnergy;
    @Column(name = "heartbeat_interval")
    private String heartBeatInterval;
    @Column(name = "channel_id")
    private String channelId;
    @Column(name = "pan_id")
    private String panId;
    @Column(name = "frequncy")
    private String frequency;
    @Column(name = "parent_mac")
    private String parentMac;
    @Column(name = "router_mac")
    private String routerMac;
    @Column(name = "connected")
    private Integer connected;
    @Column(name = "type")
    private String type;
    @Column(name = "area_id")
    private Integer areaId;
    @Column(name = "addr")
    private String addr;
    @Column(name = "in_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inTime;
    @Column(name = "expire_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;
    @Column(name = "notice_flag")
    private Integer noticeFlag;
    @Column(name = "current_member_id")
    private Integer currentMemberId;
    @Column(name = "paid")
    private Integer paid;
    @Column(name = "last_seen_time")
    private Date lastSeenTime;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "rec_st")
    private Integer recSt = 1;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "mode")
    private String mode;
    @Column(name="hardware_version")
    private String hardwareVersion;

    @Column(name = "lth")
    private String lthreshold;
    @Column(name = "hth")
    private String hthreshold;
    @Column(name = "wlth")
    private String waveLthreshold;
    @Column(name = "whth")
    private String waveHthreshold;
    @Column(name = "wint")
    private String windowTime;
    @Column(name = "sit")
    private String steadyDelayIn;
    @Column(name = "sot")
    private String steadyDelayOut;
    @Column(name = "exitv")
    private String extractionInterval;
    @Column(name = "zth")
    private String zAngleThreshold;
    @Column(name = "sltol")
    private String soltTotalTime;
    @Column(name = "slec")
    private String soltEqualCopies;
    @Column(name = "slpst")
    private String soltPosition;
    @Column(name = "rssi")
    private String rssi;
    @Column(name = "lqi")
    private String lqi;
    @Column(name = "lt")
    private String lt;

    /**
     * 2017-04-17添加每次车进车出添加流水id
     */
    @Column(name = "log_id")
    private String logId;
    @Column(name = "happen_time")
    private Date happenTime;
    @Column(name = "lot_no")
    private String lotNo;


    @Column(name = "bluetooth_array")
    private String bluetoothArray;
    @Column(name = "bluetooth")
    private String bluetooth;



    /**
     * 2019年4月23日新增
     */
    @Column(name = "vedio_status")
    private String vedioStatus;
    @Column(name = "vedio_time")
    private String vedioTime;
    @Column(name = "sensor_time")
    private String sensorTime;
    @Column(name = "cph")
    private String cph;
    @Column(name = "cp_color")
    private String cpColor;
    @Column(name = "camera_id")
    private String cameraId;
    @Column(name = "camera_name")
    private String cameraName;
    @Column(name = "pic_link")
    private String picLink;

    @Column(name = "sensor_status")
    private Integer sensorStatus;


}
