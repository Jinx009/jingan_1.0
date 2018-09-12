package com.protops.gateway.handler.v1_0;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.domain.SensorRegisterRequest;
import com.protops.gateway.domain.SensorRegisterResponse;
import com.protops.gateway.domain.base.IOTRequest;
import com.protops.gateway.domain.base.IOTResponse;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.exception.IOTException;
import com.protops.gateway.handler.BaseHandler;
import com.protops.gateway.service.SensorService;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.util.StringUtils;
import com.protops.gateway.vo.internal.SensorVo;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by damen on 2016/4/4.
 */
public class SensorRegisterHandler extends BaseHandler<SensorRegisterRequest, SensorRegisterResponse> {

    @Autowired
    SensorService sensorService;

    @Override
    public void doPreHandle(IOTRequest<SensorRegisterRequest> iotRequest, IOTResponse<SensorRegisterResponse> iotResponse, IOTContext iotContext) throws IOTException {

    }

    @Override
    public void doPostHandle(IOTRequest<SensorRegisterRequest> iotRequest, IOTResponse<SensorRegisterResponse> iotResponse, IOTContext iotContext) throws IOTException {

    }

    @Override
    public void process(IOTRequest<SensorRegisterRequest> request, IOTResponse<SensorRegisterResponse> response, IOTContext iotContext) throws IOTException {

        SensorRegisterRequest sensorRegisterRequest = request.getDomain();

        List<SensorVo> ssr = sensorRegisterRequest.getSsr();

        StringBuilder sb = new StringBuilder();

        for (SensorVo sensorVo : ssr) {

            try {

                Sensor sensorGet = sensorService.getByMac(sensorVo.getMac());

                if (sensorGet != null) {
                    continue;
                }

                Sensor sensor = SensorVo.parse(sensorVo);

                sensorService.save(sensor);

            }catch(Exception e){

                sb.append(sensorVo.getUid());
                sb.append(",");

            }

        }

        if(StringUtils.isNotBlank(sb.toString())){

            String failedList = sb.substring(0, (sb.length() - 1));

            SensorRegisterResponse sensorRegisterResponse = new SensorRegisterResponse();

            sensorRegisterResponse.setFailedList(failedList);

            response.setDomain(sensorRegisterResponse);

        }

    }

    @Override
    public IOTRequest<SensorRegisterRequest> parseRequest(byte[] requestBody) {
        return JsonUtil.fromJsonWithCustomObject(requestBody, new TypeReference<IOTRequest<SensorRegisterRequest>>() {});
    }
}
