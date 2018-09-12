package com.protops.gateway.domain;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.domain.base.IOTDomain;
import com.protops.gateway.exception.IOTException;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.util.SignUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;

/**
 * Created by damen on 2016/3/23.
 */
public class HeartBeatResponse extends IOTDomain {

    private String cmd;
    @JsonProperty("jid")
    private String jobId;
    @JsonProperty("jd")
    private Map<String, Object> jobDetail;

    @Override
    public void validateDomain() throws IOTException {

    }

    @Override
    public String parseSignStr(IOTContext iotContext) {

        StringBuilder sb = new StringBuilder();

        genKeyValuePairWithAnd(sb, "cmd", getCmd() == null ? "" : getCmd(), iotContext);
        genKeyValuePairWithAnd(sb, "jid", getJobId() == null ? "" : getJobId(), iotContext);

        String result = StringUtils.EMPTY;
        if (getJobDetail() != null) {

            String jobDetail = JsonUtil.toJson(getJobDetail());

            result = SignUtils.md5(jobDetail);

        }

        genKeyValuePair(sb, "jd", result == null ? "" : result, iotContext);

        return sb.toString();
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Map<String, Object> getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(Map<String, Object> jobDetail) {
        this.jobDetail = jobDetail;
    }
}
