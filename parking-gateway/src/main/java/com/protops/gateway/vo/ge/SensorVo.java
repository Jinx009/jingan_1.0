package com.protops.gateway.vo.ge;

import com.protops.gateway.util.PreciseDateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * Created by fanlin on 16/7/5.
 */
public class SensorVo {

    //hard from comm
    private Integer id;
    private String mac;
    private String uid;
    private String description;
    private String firmwareVersion;
    private String batteryVoltage;
    private String xMag;
    private String yMag;
    private String zMag;
    private Integer available;
    private String baseEnergy;
    private String heartBeatInterval;
    private String channelId;
    private String panId;
    private String frequency;
    private String parentMac;
    private String routerMac;
    private Integer connected;
    private String type;
    private String mode;
    private String hardwareVersion;

    @JsonSerialize(using = PreciseDateSerializer.class)
    private Date lastSeenTime;
    @JsonSerialize(using = PreciseDateSerializer.class)
    private Date createTime;
    @JsonSerialize(using = PreciseDateSerializer.class)
    private Date updateTime;

    //editable
    private Integer areaId;
    private String longitude;
    private String latitude;

    ///sensor for biz
    private String addr;
    @JsonSerialize(using = PreciseDateSerializer.class)
    private Date inTime;
    @JsonSerialize(using = PreciseDateSerializer.class)
    private Date expireTime;
    private Integer noticeFlag;
    private Integer currentMemberId;
    private Integer paid;

    //area info
    private String areaName;
    private Integer locationId;
    private Integer areaLevel;
    private String areaTag;
    private String areaDescription;
    private Integer chargePolicyId;

    //location info
    private String locationName;
    private Integer locationParentId;
    private Integer locationLevel;
    private String locationTag;
    private String locationDescription;



    private String bluetooth;

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
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

    public Integer getConnected() {
        return connected;
    }

    public void setConnected(Integer connected) {
        this.connected = connected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLastSeenTime() {
        return lastSeenTime;
    }

    public void setLastSeenTime(Date lastSeenTime) {
        this.lastSeenTime = lastSeenTime;
    }

    public Date getCreateTime() {

        return createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getNoticeFlag() {
        return noticeFlag;
    }

    public void setNoticeFlag(Integer noticeFlag) {
        this.noticeFlag = noticeFlag;
    }

    public Integer getCurrentMemberId() {
        return currentMemberId;
    }

    public void setCurrentMemberId(Integer currentMemberId) {
        this.currentMemberId = currentMemberId;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getAreaTag() {
        return areaTag;
    }

    public void setAreaTag(String areaTag) {
        this.areaTag = areaTag;
    }

    public String getAreaDescription() {
        return areaDescription;
    }

    public void setAreaDescription(String areaDescription) {
        this.areaDescription = areaDescription;
    }

    public Integer getChargePolicyId() {
        return chargePolicyId;
    }

    public void setChargePolicyId(Integer chargePolicyId) {
        this.chargePolicyId = chargePolicyId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getLocationParentId() {
        return locationParentId;
    }

    public void setLocationParentId(Integer locationParentId) {
        this.locationParentId = locationParentId;
    }

    public Integer getLocationLevel() {
        return locationLevel;
    }

    public void setLocationLevel(Integer locationLevel) {
        this.locationLevel = locationLevel;
    }

    public String getLocationTag() {
        return locationTag;
    }

    public void setLocationTag(String locationTag) {
        this.locationTag = locationTag;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }
}
