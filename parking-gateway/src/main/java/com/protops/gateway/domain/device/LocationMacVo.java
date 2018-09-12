package com.protops.gateway.domain.device;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jinx on 4/19/17.
 */
@Getter
@Setter
public class LocationMacVo {

    private String mac;
    private Integer location;
    private Integer area;
    private String lotNo;

}
