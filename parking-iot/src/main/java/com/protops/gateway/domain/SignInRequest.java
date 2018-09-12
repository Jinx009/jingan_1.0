package com.protops.gateway.domain;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.constants.enums.Version;
import com.protops.gateway.domain.base.IOTDomain;
import com.protops.gateway.exception.IOTException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by damen on 2016/3/23.
 */
public class SignInRequest extends IOTDomain {

    @JsonProperty("mc")
    private String mac;
    private String uid;
    @JsonProperty("dc")
    private String description;
    @JsonProperty("cid")
    private String channelId;
    @JsonProperty("pid")
    private String panId;
    @JsonProperty("hbi")
    private String heartbeatInterval;
    @JsonProperty("iv")
    private String interfaceVersion;
    @JsonProperty("fv")
    private String firmwareVersion;
    @JsonProperty("fc")
    private String frequency;
    @JsonProperty("hv")
    private String harwareVersion;
    @JsonProperty("rhv")
    private String rHardwareVersion;


    @Override
    public void validateDomain() throws IOTException {






    }

    @Override
    public String parseSignStr(IOTContext iotContext) {

        StringBuilder sb = new StringBuilder();

        genKeyValuePairWithAnd(sb, "cid", getChannelId() == null ? "" : getChannelId(), iotContext);
        genKeyValuePairWithAnd(sb, "dc", getDescription() == null ? "" : getDescription(), iotContext); //1 不参与签名
        genKeyValuePairWithAnd(sb, "fc", getFrequency() == null ? "" : getFrequency(), iotContext);
        genKeyValuePairWithAnd(sb, "fv", getFirmwareVersion() == null ? "" : getFirmwareVersion(), iotContext);
        genKeyValuePairWithAnd(sb, "hbi", getHeartbeatInterval() == null ? "" : getHeartbeatInterval(), iotContext);
        if(iotContext.getVersion() != Version.v1_0) {
            genKeyValuePairWithAnd(sb, "hv", getHarwareVersion() == null ? "" : getHarwareVersion(), iotContext);
        }
        genKeyValuePairWithAnd(sb, "iv", getInterfaceVersion() == null ? "" : getInterfaceVersion(), iotContext);
        genKeyValuePairWithAnd(sb, "mc", getMac() == null ? "" : getMac(), iotContext);
        genKeyValuePairWithAnd(sb, "pid", getPanId() == null ? "" : getPanId(), iotContext);
        if(iotContext.getVersion() != Version.v1_0) {
            genKeyValuePairWithAnd(sb, "rhv", getrHardwareVersion() == null ? "" : getrHardwareVersion(), iotContext);
        }
        genKeyValuePair(sb, "uid", getUid() == null ? "" : getUid(), iotContext);

        return sb.toString();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(String heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public String getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(String interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    @Override
    public boolean routerCheckIgnore() {
        return true;
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
