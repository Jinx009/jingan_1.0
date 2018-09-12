package com.protops.gateway.domain.iot;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhouzhihao on 2016/11/29.
 */
@Entity
@Table(name = "tbl_error_flow")
@Getter
@Setter
public class ErrorFlow {

    public static Integer STATUS_OK = 1;
    public static Integer STATUS_INIT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "parent_mac")
    private String parentMac;
    @Column(name = "serial_no")
    private String serialNo;
    @Column(name = "log_time")
    private String logTime;
    @Column(name = "error_bitmap")
    private String errorBitMap;
    @Column(name = "mac")
    private String mac;
    @Column(name = "type")
    private String type;
    @Column(name = "status")
    private Integer status = STATUS_INIT;
    @Column(name = "rec_st")
    private Integer recSt = 1;
    @Column(name = "area_id")
    private Integer areaId;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

}
