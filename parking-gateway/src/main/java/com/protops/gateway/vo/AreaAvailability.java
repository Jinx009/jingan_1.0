package com.protops.gateway.vo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by damen on 2016/4/16.
 */
public class AreaAvailability {

    @JsonProperty("percent")
    private String percent;
    @JsonProperty("unit")
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
