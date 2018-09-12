package com.protops.gateway.domain.log;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tbl_sensor_inoutmoneylog")
public class SensorInOutMoneyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "happen_time")
    @Temporal(TemporalType.DATE)
    private Date happenTime;
    @Column(name = "money")
    private Double money;
    @Column(name = "area_id")
    private Integer areaId;

}
