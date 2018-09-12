package com.protops.gateway.vo.internal;

import com.protops.gateway.domain.log.BluetoothLog;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by jinx on 6/23/17.
 */
@Getter
@Setter
public class BluetoothLogVo {

    private String reportTime;
    private Integer status;
    private String mac;
    private List<BluetoothLogBo> bluetooths;

}
