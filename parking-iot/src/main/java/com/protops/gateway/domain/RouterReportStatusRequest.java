package com.protops.gateway.domain;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.constants.enums.Version;
import com.protops.gateway.domain.base.IOTDomain;
import com.protops.gateway.exception.IOTException;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by damen on 2016/3/23.
 */
public class RouterReportStatusRequest extends IOTDomain {

    @JsonProperty("mc")
    private String mac;
    @JsonProperty("fv")
    private String firmwareVersion;
    @JsonProperty("rfv")
    private String routerFirmwareVersion;
    @JsonProperty("iv")
    private String interfaceVersion;

    @JsonProperty("hbi")
    private String heartBeatInterval;
    @JsonProperty("cid")
    private String channelId;
    @JsonProperty("pid")
    private String panId;
    @JsonProperty("fc")
    private String frequency;
    @JsonProperty("hv")
    private String harwareVersion;
    @JsonProperty("rhv")
    private String rHardwareVersion;

    @Override
    public String parseSignStr(IOTContext iotContext) {

        StringBuilder sb = new StringBuilder();

        genKeyValuePairWithAnd(sb, "cid", getChannelId() == null ? "" : getChannelId(), iotContext);
        genKeyValuePairWithAnd(sb, "fc", getFrequency() == null ? "" : getFrequency(), iotContext);
        genKeyValuePairWithAnd(sb, "fv", getFirmwareVersion() == null ? "" : getFirmwareVersion(), iotContext);
        genKeyValuePairWithAnd(sb, "hbi", getHeartBeatInterval() == null ? "" : getHeartBeatInterval(), iotContext);
        if(iotContext.getVersion() != Version.v1_0) {
            genKeyValuePairWithAnd(sb, "hv", getHarwareVersion() == null ? "" : getHarwareVersion(), iotContext);
        }
        genKeyValuePairWithAnd(sb, "iv", getInterfaceVersion() == null ? "" : getInterfaceVersion(), iotContext);
        genKeyValuePairWithAnd(sb, "mc", getMac() == null ? "" : getMac(), iotContext);
        genKeyValuePairWithAnd(sb, "pid", getPanId() == null ? "" : getPanId(), iotContext);

        if(iotContext.getVersion() != Version.v1_0) {
            genKeyValuePairWithAnd(sb, "rfv", getRouterFirmwareVersion() == null ? "" : getRouterFirmwareVersion(), iotContext);
            genKeyValuePair(sb, "rhv", getrHardwareVersion() == null ? "" : getrHardwareVersion(), iotContext);
        }else{
            genKeyValuePair(sb, "rfv", getRouterFirmwareVersion() == null ? "" : getRouterFirmwareVersion(), iotContext);
        }


        return sb.toString();
    }

    @Override
    public void validateDomain() throws IOTException {

    }


    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }


    public String getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(String interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

    public String getRouterFirmwareVersion() {
        return routerFirmwareVersion;
    }

    public void setRouterFirmwareVersion(String routerFirmwareVersion) {
        this.routerFirmwareVersion = routerFirmwareVersion;
    }

    public String getHarwareVersion() {
        return harwareVersion;
    }

    public void setHarwareVersion(String harwareVersion) {
        this.harwareVersion = harwareVersion;
    }

    public String getrHardwareVersion() {
        return rHardwareVersion;
    }

    public void setrHardwareVersion(String rHardwareVersion) {
        this.rHardwareVersion = rHardwareVersion;
    }
}
