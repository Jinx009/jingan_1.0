package com.protops.gateway.handler.v1_0;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.domain.ReportStatusRequest;
import com.protops.gateway.domain.ReportStatusResponse;
import com.protops.gateway.domain.RouterReportStatusRequest;
import com.protops.gateway.domain.RouterReportStatusResponse;
import com.protops.gateway.domain.base.IOTRequest;
import com.protops.gateway.domain.base.IOTResponse;
import com.protops.gateway.domain.iot.Router;
import com.protops.gateway.exception.IOTException;
import com.protops.gateway.handler.BaseHandler;
import com.protops.gateway.service.RouterService;
import com.protops.gateway.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by damen on 2016/3/29.
 */
public class RouterReportStatusHandler extends BaseHandler<RouterReportStatusRequest, RouterReportStatusResponse> {


    @Autowired
    RouterService routerService;

    @Override
    public void doPreHandle(IOTRequest<RouterReportStatusRequest> iotRequest, IOTResponse<RouterReportStatusResponse> iotResponse, IOTContext iotContext) throws IOTException {

    }

    @Override
    public void doPostHandle(IOTRequest<RouterReportStatusRequest> iotRequest, IOTResponse<RouterReportStatusResponse> iotResponse, IOTContext iotContext) throws IOTException {

    }

    @Override
    public void process(IOTRequest<RouterReportStatusRequest> request, IOTResponse<RouterReportStatusResponse> response, IOTContext iotContext) throws IOTException {

        RouterReportStatusRequest reportStatusRequest = request.getDomain();

        Router router = routerService.getByMac(request.getMac());

        if (router == null) {
            throw new IOTException(ResponseCodes.Sys.PARAM_ILLEGAL);
        }

        if (StringUtils.isNotBlank(reportStatusRequest.getFrequency())) {
            router.setFrequency(reportStatusRequest.getFrequency());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getMac())) {
            router.setMac(reportStatusRequest.getMac());
        }

        if (StringUtils.isNotBlank(reportStatusRequest.getChannelId())) {
            router.setChannelId(reportStatusRequest.getChannelId());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getFirmwareVersion())) {
            router.setFirmwareVersion(reportStatusRequest.getFirmwareVersion());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getHeartBeatInterval())) {
            router.setHeartbeatInterval(reportStatusRequest.getHeartBeatInterval());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getPanId())) {
            router.setPanId(reportStatusRequest.getPanId());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getInterfaceVersion())) {
            router.setInterfaceVersion(reportStatusRequest.getInterfaceVersion());
        }
        if (StringUtils.isNotBlank(reportStatusRequest.getRouterFirmwareVersion())) {
            router.setRouterFirmwareVersion(reportStatusRequest.getRouterFirmwareVersion());
        }
        router.setLastSeenTime(new Date());

        routerService.save(router);

    }

    @Override
    public IOTRequest<RouterReportStatusRequest> parseRequest(byte[] requestBody) {
        return JsonUtil.fromJsonWithCustomObject(requestBody, new TypeReference<IOTRequest<RouterReportStatusRequest>>() {
        });
    }
}
