package com.protops.gateway.domain.log;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;

/**
 * Created by jinx on 6/23/17.
 */
@Setter
@Getter
@Entity
@Table(name = "tbl_bluetooth_log")
public class BluetoothLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "uuid_value")
    private String uuid;
    @Column(name = "signal_value")
    private String signalValue;
    @Column(name = "report_time")
    private String reportTime;
    @Column(name = "status")
    private Integer status;
    @Column(name = "router_mac")
    private String routerMac;
    @Column(name = "mac")
    private String mac;

}
