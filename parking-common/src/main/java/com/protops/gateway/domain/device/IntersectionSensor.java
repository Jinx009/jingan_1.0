package com.protops.gateway.domain.device;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jinx on 3/13/17.
 * 路口sensor
 */
@Setter
@Getter
@Entity
@Table(name = "tbl_intersectionsensor")
public class IntersectionSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "mac")
    private String mac;//设备mac号
    @Column(name = "uid")
    private String uid;
    @Column(name = "description")
    private String desc;
    @Column(name = "firmware_version")
    private String firmwareVersion;//接收机软件版本
    @Column(name = "battery_voltage")
    private String batteryVoltage;//电压
    @Column(name = "x_mag")
    private String xMag;
    @Column(name = "y_mag")
    private String yMag;
    @Column(name = "z_mag")
    private String zMag;
    @Column(name = "base_energy")
    private String baseEnergy;//基准值
    @Column(name = "heartbeat_interval")
    private String heartBeatInterval;//心跳间隔
    @Column(name = "channel_id")
    private String channelId;//信道id
    @Column(name = "pan_id")
    private String panId;//PANID网络标识号
    @Column(name = "frequency")
    private String frequency;//射频频率
    @Column(name = "parent_mac")
    private String parentMac;//父级mac
    @Column(name = "router_mac")
    private String routerMac;//接收机mac
    @Column(name = "connected")
    private Integer connected;//是否连接
    @Column(name = "type")
    private String type;
    @Column(name = "addr")
    private String addr;
    @Column(name = "mode")
    private String mode;
    @Column(name="hardware_version")
    private String hardwareVersion;//硬件版本
    @Column(name = "test_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date testTime;//测试时间
    /**
     * 后台配置的数据
     */
    @Column(name = "location")
    private Integer location;
    @Column(name = "area_id")
    private Integer areaId;//区域id
    @Column(name = "lid")
    private Integer lid;//车道
    @Column(name = "longitude")
    private String longitude;//经度
    @Column(name = "latitude")
    private String latitude;//纬度
    @Column(name = "rec_st")
    private Integer recSt = 1;//是否可用
    @Column(name = "pos")
    private String pos;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//插入时间


    @Column(name = "direction")
    private String direction;
    @Column(name = "in_or_out")
    private String inOrOut;
    @Column(name = "lid_in_direction")
    private String lidIndirection;
    @Column(name = "direction_on_lid")
    private String directionOnLid;//转向
    /**
     * 不会改变的数据
     */
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//创建时间

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

}
