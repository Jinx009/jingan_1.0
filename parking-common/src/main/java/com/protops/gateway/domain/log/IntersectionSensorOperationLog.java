package com.protops.gateway.domain.log;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jinx on 3/13/17.
 * 路口sensor业务数据
 */
@Setter
@Getter
@Entity
@Table(name = "tbl_intersectionsensor_operationlog")
public class IntersectionSensorOperationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="mac")
    private String mac;
    @Column(name = "lid")
    private Integer lid;//车道
    @Column(name = "car_num")
    private Integer carNum;//车流量
    @Column(name = "pos")
    private String pos;//车流方向
    @Column(name = "occupy")
    private String occupy;//占有率
    @Column(name = "test_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date testTime;//测试时间
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "area_id")
    private Integer areaId;
    @Column(name = "direction")
    private String direction;
    @Column(name = "in_or_out")
    private String inOrOut;
    @Column(name = "lid_in_direction")
    private String lidIndirection;
    @Column(name = "direction_on_lid")
    private String directionOnLid;//转向

}
