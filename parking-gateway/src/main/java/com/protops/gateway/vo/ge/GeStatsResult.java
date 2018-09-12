package com.protops.gateway.vo.ge;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by damen on 2016/4/16.
 */
public class GeStatsResult {

    @JsonProperty("利用率")
    private String percent;
    @JsonProperty("period")
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
