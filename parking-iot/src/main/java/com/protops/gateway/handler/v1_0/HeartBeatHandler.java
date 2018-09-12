package com.protops.gateway.handler.v1_0;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.constants.enums.iot.JobStatus;
import com.protops.gateway.domain.HeartBeatRequest;
import com.protops.gateway.domain.HeartBeatResponse;
import com.protops.gateway.domain.base.IOTRequest;
import com.protops.gateway.domain.base.IOTResponse;
import com.protops.gateway.domain.iot.Job;
import com.protops.gateway.exception.IOTException;
import com.protops.gateway.handler.BaseHandler;
import com.protops.gateway.service.JobService;
import com.protops.gateway.service.RouterService;
import com.protops.gateway.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Created by damen on 2016/4/1.
 */
public class HeartBeatHandler extends BaseHandler<HeartBeatRequest, HeartBeatResponse> {

    @Autowired
    JobService jobService;

    @Autowired
    RouterService routerService;


    @Override
    public void doPreHandle(IOTRequest<HeartBeatRequest> iotRequest, IOTResponse<HeartBeatResponse> iotResponse, IOTContext iotContext) throws IOTException {


    }

    @Override
    public void doPostHandle(IOTRequest<HeartBeatRequest> iotRequest, IOTResponse<HeartBeatResponse> iotResponse, IOTContext iotContext) throws IOTException {
        routerService.updateTime(iotRequest.getMac(), new Date());
    }

    @Override
    public void process(IOTRequest<HeartBeatRequest> request, IOTResponse<HeartBeatResponse> response, IOTContext iotContext) throws IOTException {

        HeartBeatRequest heartBeatRequest = request.getDomain();

        // 如果什么都没有送
        if (StringUtils.isBlank(heartBeatRequest.getJobId()) || StringUtils.isBlank(heartBeatRequest.getJobStatus())) {

            // 找到1，初始化，2，未接收的job
            Job job = jobService.getAvailableJobByMac(iotContext.getRouter().getMac());

            // 找不到，就直接正常返回
            if (job == null) {
                return;
            } else {
                // 如果找到
                buildResponse(response, job);
                return;

            }

        } else if (StringUtils.isNotBlank(heartBeatRequest.getJobId()) && StringUtils.isNotBlank(heartBeatRequest.getJobStatus())) {

            Integer jobId = Integer.valueOf(heartBeatRequest.getJobId());
            Integer jobStatus = Integer.valueOf(heartBeatRequest.getJobStatus());

            // 如果送了
            Job job = jobService.getById(jobId);

            if (job == null) {
                throw new IOTException(ResponseCodes.Sys.SYS_ERR);
            }

            JobStatus jobStatusEnum = JobStatus.parse(jobStatus);
            if (jobStatusEnum == null) {
                throw new IOTException(ResponseCodes.Sys.SYS_ERR);
            }

            // 只要是初始化状态，就始终返回job任务。
            if (jobStatusEnum == JobStatus.init) {

                buildResponse(response, job);

                return;

            }

            // 如果是其他状态，比如已收到，或者成功失败，就只是更新，不在返回数据
            jobService.updateStatus(job.getId(), jobStatusEnum);

        }
    }

    @Override
    public IOTRequest<HeartBeatRequest> parseRequest(byte[] requestBody) {
        return JsonUtil.fromJsonWithCustomObject(requestBody, new TypeReference<IOTRequest<HeartBeatRequest>>() {});
    }

    private void buildResponse(IOTResponse<HeartBeatResponse> response, Job job) {

        HeartBeatResponse heartBeatResponse = new HeartBeatResponse();

        heartBeatResponse.setCmd(job.getCmd());
        heartBeatResponse.setJobId(String.valueOf(job.getId()));
        heartBeatResponse.setJobDetail(JsonUtil.fromJson(job.getJobDetail(), Map.class));

        response.setDomain(heartBeatResponse);
    }
}
