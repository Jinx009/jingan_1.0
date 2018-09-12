package com.protops.gateway.domain.device;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "tbl_tianyiiot_device")
public class TianyiIoTDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "mac")
    private String mac;//设备mac号
    @Column(name = "imei")
    private String imei;
    @Column(name = "device_id")
    private String deviceId;

}
