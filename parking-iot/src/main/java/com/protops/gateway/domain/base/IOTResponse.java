package com.protops.gateway.domain.base;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by damen on 2014/10/21.
 */
@JsonPropertyOrder({"m", "s", "rc", "rm", "d"})
public class IOTResponse<T extends IOTDomain> extends BaseMessage {

    @JsonProperty("rc")
    private String respCode = "00";
    @JsonProperty("rm")
    private String responseMsg = "";

    @JsonProperty("d")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private T domain;

    public IOTResponse(BaseMessage IOTRequest) {

        this.setMac(IOTRequest.getMac());
        this.setSign(StringUtils.EMPTY);
    }

    public T getDomain() {
        return domain;
    }

    public void setDomain(T domain) {
        this.domain = domain;
    }


    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
}