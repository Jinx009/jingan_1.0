package com.protops.gateway.handler.v1_0;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.domain.ErrorFlowRequest;
import com.protops.gateway.domain.HeartBeatResponse;
import com.protops.gateway.domain.base.IOTRequest;
import com.protops.gateway.domain.base.IOTResponse;
import com.protops.gateway.domain.iot.ErrorFlow;
import com.protops.gateway.exception.IOTException;
import com.protops.gateway.handler.BaseHandler;
import com.protops.gateway.service.ErrorFlowService;
import com.protops.gateway.util.JsonUtil;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by damen on 2016/11/27.
 */
public class ErrorFlowHandler extends BaseHandler<ErrorFlowRequest, HeartBeatResponse> {

    @Autowired
    ErrorFlowService errorFlowService;

    public void doPreHandle(IOTRequest<ErrorFlowRequest> iotRequest, IOTResponse<HeartBeatResponse> iotResponse, IOTContext iotContext) throws IOTException {

    }

    public void doPostHandle(IOTRequest<ErrorFlowRequest> iotRequest, IOTResponse<HeartBeatResponse> iotResponse, IOTContext iotContext) throws IOTException {

    }

    public void process(IOTRequest<ErrorFlowRequest> request, IOTResponse<HeartBeatResponse> response, IOTContext iotContext) throws IOTException {

        ErrorFlowRequest errorFlowRequest = request.getDomain();

//        ErrorFlow errorFlowGet = errorFlowService.getBySerialNo(errorFlowRequest.getSerialNo());
//
//        if (errorFlowGet != null) {
//
//            errorFlowGet.setMac(errorFlowRequest.getMac());
//            errorFlowGet.setErrorBitMap(errorFlowRequest.getErrorBitMap());
//            errorFlowGet.setLogTime(errorFlowRequest.getLogTime());
//            errorFlowGet.setParentMac(errorFlowRequest.getParentMac());
//            errorFlowGet.setSerialNo(errorFlowRequest.getSerialNo());
//            errorFlowGet.setType(errorFlowRequest.getType());
//
//        }

        ErrorFlow errorFlow = new ErrorFlow();

        errorFlow.setMac(errorFlowRequest.getMac());
        errorFlow.setErrorBitMap(errorFlowRequest.getErrorBitMap());
        errorFlow.setLogTime(errorFlowRequest.getLogTime());
        errorFlow.setParentMac(errorFlowRequest.getParentMac());
        errorFlow.setSerialNo(errorFlowRequest.getSerialNo());
        errorFlow.setType(errorFlowRequest.getType());

        errorFlowService.save(errorFlow);

    }

    @Override
    public IOTRequest<ErrorFlowRequest> parseRequest(byte[] requestBody) {
        return JsonUtil.fromJsonWithCustomObject(requestBody, new TypeReference<IOTRequest<ErrorFlowRequest>>() {
        });
    }
}
