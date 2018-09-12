package com.protops.gateway.domain;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.constants.enums.Version;
import com.protops.gateway.domain.base.IOTDomain;
import com.protops.gateway.exception.IOTException;
import com.protops.gateway.vo.internal.SensorVo;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by damen on 2016/3/23.
 */
@Getter
@Setter
public class ReportStatusRequest extends IOTDomain {

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
    @JsonProperty("md")
    private String mode;
    @JsonProperty("hv")
    private String harwareVersion;

    @JsonProperty("lth")
    private String lthreshold;
    @JsonProperty("hth")
    private String hthreshold;
    @JsonProperty("wlth")
    private String waveLthreshold;
    @JsonProperty("whth")
    private String waveHthreshold;
    @JsonProperty("wint")
    private String windowTime;
    @JsonProperty("sit")
    private String steadyDelayIn;
    @JsonProperty("sot")
    private String steadyDelayOut;
    @JsonProperty("exitv")
    private String extractionInterval;
    @JsonProperty("zth")
    private String zAngleThreshold;
    @JsonProperty("sltol")
    private String soltTotalTime;
    @JsonProperty("slec")
    private String soltEqualCopies;
    @JsonProperty("slpst")
    private String soltPosition;
    @JsonProperty("rssi")
    private String rssi;
    @JsonProperty("lqi")
    private String lqi;
    @JsonProperty("lt")
    private String lt;
    /**
     * 2014-04-17新增字段
     */
    @JsonProperty("ht")
    private String happenTime;
    @JsonProperty("logid")
    private String logId;


    @Override
    public String parseSignStr(IOTContext iotContext) {

        StringBuilder sb = new StringBuilder();

        genKeyValuePairWithAnd(sb, "avlb", getAvailable() == null ? "" : getAvailable(), iotContext);
        genKeyValuePairWithAnd(sb, "be", getBaseEnergy() == null ? "" : getBaseEnergy(), iotContext);
        genKeyValuePairWithAnd(sb, "bv", getBatteryVoltage() == null ? "" : getBatteryVoltage(), iotContext); // 不参与签名
        genKeyValuePairWithAnd(sb, "cid", getChannelId() == null ? "" : getChannelId(), iotContext);
        genKeyValuePairWithAnd(sb, "cnct", getConnected() == null ? "" : getConnected(), iotContext); // 不参与签名
        genKeyValuePairWithAnd(sb, "fc", getFrequency() == null ? "" : getFrequency(), iotContext);
        genKeyValuePairWithAnd(sb, "fv", getFirmwareVersion() == null ? "" : getFirmwareVersion(), iotContext);
        genKeyValuePairWithAnd(sb, "hbi", getHeartBeatInterval() == null ? "" : getHeartBeatInterval(), iotContext);

        if(iotContext.getVersion() != Version.v1_0) {
            genKeyValuePairWithAnd(sb, "hv", getHarwareVersion() == null ? "" : getHarwareVersion(), iotContext);
        }

        genKeyValuePairWithAnd(sb, "mc", getMac() == null ? "" : getMac(), iotContext);
        genKeyValuePairWithAnd(sb, "md", getMode() == null ? "" : getMode(), iotContext);
        genKeyValuePairWithAnd(sb, "pid", getPanId() == null ? "" : getPanId(), iotContext);
        genKeyValuePairWithAnd(sb, "pm", getParentMac() == null ? "" : getParentMac(), iotContext);
        genKeyValuePairWithAnd(sb, "rm", getRouterMac() == null ? "" : getRouterMac(), iotContext);
        genKeyValuePairWithAnd(sb, "tp", getType() == null ? "" : getType(), iotContext);
        genKeyValuePairWithAnd(sb, "x", getXMag() == null ? "" : getXMag(), iotContext);
        genKeyValuePairWithAnd(sb, "y", getYMag() == null ? "" : getYMag(), iotContext);
        genKeyValuePair(sb, "z", getZMag() == null ? "" : getZMag(), iotContext);


        return sb.toString();
    }

    @Override
    public void validateDomain() throws IOTException {

    }


}
