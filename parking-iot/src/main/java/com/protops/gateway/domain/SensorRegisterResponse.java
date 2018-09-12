package com.protops.gateway.domain;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.domain.base.IOTDomain;
import com.protops.gateway.exception.IOTException;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by damen on 2016/4/4.
 */
public class SensorRegisterResponse extends IOTDomain {

    @JsonProperty("fl")
    private String failedList;

    @Override
    public void validateDomain() throws IOTException {

    }

    @Override
    public String parseSignStr(IOTContext iotContext) {
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isBlank(getFailedList())) {

            return StringUtils.EMPTY;

        }

        genKeyValuePair(sb, "fl", getFailedList() == null ? "" : getFailedList(), iotContext);

        return sb.toString();
    }

    public String getFailedList() {
        return failedList;
    }

    public void setFailedList(String failedList) {
        this.failedList = failedList;
    }
}
