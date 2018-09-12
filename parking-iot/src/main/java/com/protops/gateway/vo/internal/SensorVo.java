package com.protops.gateway.vo.internal;

import com.protops.gateway.domain.iot.Sensor;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by damen on 2016/3/29.
 */
public class SensorVo {

    @JsonProperty("mc")
    private String mac;
    @JsonProperty("fv")
    private String firmwareVersion;
    @JsonProperty("bv")
    private String batteryVoltage;
    @JsonProperty("x")
    private String xMag;
    @JsonProperty("y")
    private String yMag;
    @JsonProperty("z")
    private String zMag;
    @JsonProperty("avlb")
    private String available;
    @JsonProperty("be")
    private String baseEnergy;
    @JsonProperty("hbi")
    private String heartBeatInterval;
    @JsonProperty("cid")
    private String channelId;
    @JsonProperty("pid")
    private String panId;
    @JsonProperty("fc")
    private String frequency;
    @JsonProperty("pm")
    private String parentMac;
    @JsonProperty("rm")
    private String routerMac;
    @JsonProperty("cnct")
    private String connected;
    @JsonProperty("tp")
    private String type;
    @JsonProperty("dc")
    private String description;
    @JsonProperty("hv")
    private String hardwareVersion;

    private String uid;

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(String batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public String getxMag() {
        return xMag;
    }

    public void setxMag(String xMag) {
        this.xMag = xMag;
    }

    public String getyMag() {
        return yMag;
    }

    public void setyMag(String yMag) {
        this.yMag = yMag;
    }

    public String getzMag() {
        return zMag;
    }

    public void setzMag(String zMag) {
        this.zMag = zMag;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getBaseEnergy() {
        return baseEnergy;
    }

    public void setBaseEnergy(String baseEnergy) {
        this.baseEnergy = baseEnergy;
    }

    public String getHeartBeatInterval() {
        return heartBeatInterval;
    }

    public void setHeartBeatInterval(String heartBeatInterval) {
        this.heartBeatInterval = heartBeatInterval;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getPanId() {
        return panId;
    }

    public void setPanId(String panId) {
        this.panId = panId;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getParentMac() {
        return parentMac;
    }

    public void setParentMac(String parentMac) {
        this.parentMac = parentMac;
    }

    public String getRouterMac() {
        return routerMac;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public static Sensor parse(SensorVo sensorVo) {

        Sensor sensor = new Sensor();

        sensor.setFrequency(sensorVo.getFrequency());
        sensor.setMac(sensorVo.getMac());
        sensor.setAvailable(Integer.valueOf(sensorVo.getAvailable()));
        sensor.setBaseEnergy(sensorVo.getBaseEnergy());
        sensor.setBatteryVoltage(sensorVo.getBatteryVoltage());
        sensor.setChannelId(sensorVo.getChannelId());
        sensor.setConnected(Integer.valueOf(sensorVo.getConnected()));
        sensor.setDesc(sensorVo.getDescription());
        sensor.setFirmwareVersion(sensorVo.getFirmwareVersion());
        sensor.setHeartBeatInterval(sensorVo.getHeartBeatInterval());
        sensor.setPanId(sensorVo.getPanId());
        sensor.setRouterMac(sensorVo.getRouterMac());
        sensor.setParentMac(sensorVo.getParentMac());
        sensor.setXMag(sensorVo.getxMag());
        sensor.setYMag(sensorVo.getyMag());
        sensor.setZMag(sensorVo.getzMag());
        sensor.setType(sensorVo.getType());
        sensor.setUid(sensorVo.getUid());
        sensor.setHardwareVersion(sensorVo.getHardwareVersion());

        return sensor;

    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }
}
