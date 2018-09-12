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
@Table(name = "tbl_sensor_devicelog")
public class SensorDeviceLog {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "mac")
    private String mac;
    @Column(name = "uid")
    private String uid;
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
    @Column(name = "base_energy")
    private String baseEnergy;
    @Column(name = "heartbeat_interval")
    private String heartBeatInterval;
    @Column(name = "channel_id")
    private String channelId;
    @Column(name = "pan_id")
    private String panId;
    @Column(name = "frequency")
    private String frequency;
    @Column(name = "parent_mac")
    private String parentMac;
    @Column(name = "router_mac")
    private String routerMac;
    @Column(name = "connected")
    private Integer connected;
    @Column(name = "type")
    private String type;
    @Column(name = "addr")
    private String addr;
    @Column(name = "paid")
    private Integer paid;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "mode")
    private String mode;
    @Column(name="hardware_version")
    private String hardwareVersion;

    @Column(name = "dif")
    private String dif;
    @Column(name = "state")
    private String state;
    @Column(name = "ref1")
    private String ref1;
    @Column(name = "ref2")
    private String ref2;
    @Column(name = "ref3")
    private String ref3;
    @Column(name = "mod_value")
    private String mod;
    @Column(name = "ver")
    private String ver;
    @Column(name = "vol")
    private String vol;
    @Column(name = "ave1")
    private String ave1;
    @Column(name = "ave2")
    private String ave2;
    @Column(name = "ave3")
    private String ave3;
    @Column(name = "zn")
    private String zn;
    @Column(name = "zv")
    private String zv;
    @Column(name = "zt")
    private String zt;
    @Column(name = "lt")
    private String lt;
    @Column(name = "ht")
    private String ht;
    @Column(name = "wlt")
    private String wlt;
    @Column(name = "wht")
    private String wht;
    @Column(name = "at")
    private String at;
    @Column(name = "wt")
    private String wt;
    @Column(name = "sdi")
    private String sdi;
    @Column(name = "sdo")
    private String sdo;
    @Column(name = "ei")
    private String ei; //rsrp  snr  pci
    @Column(name = "rsrp")
    private String rsrp;
    @Column(name = "snr")
    private String snr;
    @Column(name = "pci")
    private String pci;
    @Column(name = "fdi")
    private String fdi;
    @Column(name = "rssi")
    private String rssi;

}
