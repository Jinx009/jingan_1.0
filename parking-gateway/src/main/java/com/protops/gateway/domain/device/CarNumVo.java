package com.protops.gateway.domain.device;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by jinx on 3/29/17.
 */
@Getter
@Setter
public class CarNumVo {

    private Date date;
    private String sort;
    private Object nums;

}
