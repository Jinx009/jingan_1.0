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
public class ErrorFlowResponse extends IOTDomain {


    @Override
    public void validateDomain() throws IOTException {

    }

    @Override
    public String parseSignStr(IOTContext iotContext) {
        return StringUtils.EMPTY;
    }

}
