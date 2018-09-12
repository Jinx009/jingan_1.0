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
public class HeartBeatRequest extends IOTDomain {

    @JsonProperty("jid")
    private String jobId;
    @JsonProperty("js")
    private String jobStatus;
    @JsonProperty("if")
    private String info;


    @Override
    public void validateDomain() throws IOTException {

        if(StringUtils.isNotBlank(jobId) && !StringUtils.isNumeric(jobId)){
            throw new IOTException(ResponseCodes.Sys.PARAM_ILLEGAL);
        }

        if(StringUtils.isNotBlank(jobStatus) && !StringUtils.isNumeric(jobStatus)){
            throw new IOTException(ResponseCodes.Sys.PARAM_ILLEGAL);
        }

    }

    @Override
    public String parseSignStr(IOTContext iotContext) {

        StringBuilder sb = new StringBuilder();

        genKeyValuePairWithAnd(sb, "if", getInfo() == null ? "" : getInfo(), iotContext);
        genKeyValuePairWithAnd(sb, "jid", getJobId() == null ? "" : getJobId(), iotContext);
        genKeyValuePair(sb, "js", getJobStatus() == null ? "" : getJobStatus(), iotContext);

        return sb.toString();
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
