package com.protops.gateway.vo.internal;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jinx on 6/26/17.
 */
@Setter
@Getter
public class BluetoothLogBo {

    private String uuid;
    private String signal;
    private String reportTime;
    private Integer status;
    private String routerMac;
    private String mac;

}
