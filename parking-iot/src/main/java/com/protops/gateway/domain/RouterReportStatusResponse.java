package com.protops.gateway.domain;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.domain.base.IOTDomain;
import com.protops.gateway.exception.IOTException;

/**
 * Created by damen on 2016/3/23.
 */
public class RouterReportStatusResponse extends IOTDomain {

    @Override
    public void validateDomain() throws IOTException {

    }

    @Override
    public String parseSignStr(IOTContext iotContext) {
        return null;
    }

}
