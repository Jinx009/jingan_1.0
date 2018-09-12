package com.protops.gateway.domain;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.domain.base.IOTDomain;
import com.protops.gateway.exception.IOTException;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by damen on 2016/3/23.
 */
public class ErrorFlowRequest extends IOTDomain {

    @JsonProperty("pm")
    private String parentMac;
    @JsonProperty("sn")
    private String serialNo;
    @JsonProperty("lt")
    private String logTime;
    @JsonProperty("ebm")
    private String errorBitMap;
    @JsonProperty("mc")
    private String mac;
    @JsonProperty("tp")
    private String type;

    @Override
    public void validateDomain() throws IOTException {

    }

    @Override
    public String parseSignStr(IOTContext iotContext) {

        StringBuilder sb = new StringBuilder();

        genKeyValuePairWithAnd(sb, "ebm", getErrorBitMap() == null ? "" : getErrorBitMap(), iotContext);
        genKeyValuePairWithAnd(sb, "lt", getLogTime() == null ? "" : getLogTime(), iotContext);
        genKeyValuePairWithAnd(sb, "mc", getMac() == null ? "" : getMac(), iotContext);
        genKeyValuePairWithAnd(sb, "pm", getParentMac() == null ? "" : getParentMac(), iotContext);
        genKeyValuePairWithAnd(sb, "sn", getSerialNo() == null ? "" : getSerialNo(), iotContext);
        genKeyValuePair(sb, "tp", getType() == null ? "" : getType(), iotContext);

        return sb.toString();
    }

    public String getParentMac() {
        return parentMac;
    }

    public void setParentMac(String parentMac) {
        this.parentMac = parentMac;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getErrorBitMap() {
        return errorBitMap;
    }

    public void setErrorBitMap(String errorBitMap) {
        this.errorBitMap = errorBitMap;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
