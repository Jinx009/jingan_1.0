package com.protops.gateway.domain.log;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jinx on 4/13/17.
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_sensor_inoutlog")
public class SensorInOutLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "in_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inTime;
    @Column(name = "out_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date outTime;
    @Column(name = "area_id")
    private Integer areaId;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "description")
    private String description;
    @Column(name = "mac")
    private String mac;
    @Column(name = "times")
    private Integer times;

    /**
     * 2017-04-17添加每次车进车出添加流水id
     */
    @Column(name = "log_id")
    private String logId;

}
