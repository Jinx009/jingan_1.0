package com.protops.gateway.vo;

import com.protops.gateway.constants.DeviceError;
import com.protops.gateway.constants.enums.DeviceType;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.domain.iot.ErrorFlow;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by damen on 2016/12/1.
 */
public class ErrorFlowVo {

    private Integer id;

    private String parentMac;
    private String logTime;
    private String mac;
    private DeviceType type;
    private DeviceError deviceError;
    private String area;
    private Integer status;

    public ErrorFlowVo(ErrorFlow errorFlow) {

        this.parentMac = errorFlow.getParentMac();
        this.logTime = errorFlow.getLogTime();
        this.mac = errorFlow.getMac();
        this.type = DeviceType.parse(errorFlow.getType());
        this.id = errorFlow.getId();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParentMac() {
        return parentMac;
    }

    public void setParentMac(String parentMac) {
        this.parentMac = parentMac;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public DeviceError getDeviceError() {
        return deviceError;
    }

    public void setDeviceError(DeviceError deviceError) {
        this.deviceError = deviceError;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
