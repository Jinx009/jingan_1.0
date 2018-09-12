package com.protops.gateway.domain.log;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jinx on 4/19/17.
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_sensor_upload_operationlog")
public class SensorUploadOperationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "test_time")
    private Date testTime;
    @Column(name = "mac")
    private String mac;
    @Column(name = "status")
    private Integer status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

}
