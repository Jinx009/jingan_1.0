package com.protops.gateway.controller;

import com.protops.gateway.constants.Constants;
import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.constants.enums.Version;
import com.protops.gateway.domain.base.IOTDomain;
import com.protops.gateway.domain.base.IOTRequest;
import com.protops.gateway.domain.base.IOTResponse;
import com.protops.gateway.domain.iot.Job;
import com.protops.gateway.domain.iot.Router;
import com.protops.gateway.exception.IOTException;
import com.protops.gateway.handler.BaseHandler;
import com.protops.gateway.service.JobService;
import com.protops.gateway.service.RouterService;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.util.SignUtils;
import com.protops.gateway.utils.CipherUtil;
import com.protops.gateway.utils.ResponseCodeHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by damen on 2016/3/23.
 */
@Controller
@RequestMapping(value = "iot")
public class IOTController implements ApplicationContextAware {

    @Autowired
    RouterService routerService;

    private ApplicationContext ctx;

    @Autowired
    JobService jobService;

    private static final Logger logger = LoggerFactory.getLogger(IOTController.class);


    private IOTResponse<IOTDomain> parseResponse(IOTRequest<IOTDomain> iotRequest) {

        return new IOTResponse(iotRequest);

    }

    @Deprecated
    @RequestMapping(value = "reverse", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public byte[] process(
            @RequestParam(value = "s") String sensorMac,
            @RequestParam(value = "r") String routerMac,
            HttpServletResponse httpServletResponse) throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("order", "F");
        map.put("data", "0");
        map.put("mac", sensorMac);

        Job job = new Job();
        job.setStatus(0);
        job.setCmd("cfgsensor");
        job.setTarget(routerMac);
        job.setJobDetail(JsonUtil.toJson(map));

        jobService.save(job);

        return "ok".getBytes("utf-8");
    }

    @RequestMapping(value = "genSign", method = RequestMethod.POST)
    @ResponseBody
    public String genSign(@RequestParam("mac") String mac, @RequestBody byte[] requestBody) {

        TreeMap<String, String> linkedHashMap = JsonUtil.fromJson(requestBody, TreeMap.class);

        String signStr = "";
        for (Map.Entry<String, String> r : linkedHashMap.entrySet()) {

            signStr += r.getKey() + "=" + r.getValue() + "&";

        }

        signStr = signStr + "mac=" + mac;

        String ret = SignUtils.sign(signStr);

        try {

            ret = com.protops.gateway.util.StringUtils.toHexString(CipherUtil.encrypt("MTJnqYLoZkjwVHA7".getBytes(Constants.Default_SysEncoding), ret.getBytes(Constants.Default_SysEncoding)));

        } catch (Exception e) {

            return null;

        }
        return ret;

    }

    /**
     * @param
     * @return
     */
    @RequestMapping(value = "{version}/{domain}/{cmd}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public IOTResponse process(
            @PathVariable(value = "version") String version,
            @PathVariable(value = "domain") String domain,
            @PathVariable(value = "cmd") String cmd,
            @RequestBody byte[] requestBody,
            HttpServletResponse httpServletResponse) throws IOException {

        logger.warn("[iotRequest][{}][{}]", cmd, new String(requestBody, Constants.Default_SysEncoding));

        IOTRequest<IOTDomain> iotRequest = null;
        IOTResponse iotResponse = null;
        BaseHandler handler = null;
        IOTContext iotContext = null;

        try {
            // 如果handler拿不到，返回400
            handler = getHandler(version, domain, cmd);
            if (handler == null) {
                return to400(httpServletResponse);
            }
            // 如果处理不了，返回400
            iotRequest = handler.parseRequest(requestBody);
            if (iotRequest == null) {
                return to400(httpServletResponse);
            }
            // 如果处理不了，返回400
            iotResponse = parseResponse(iotRequest);
            if (iotResponse == null) {
                return to400(httpServletResponse);
            }
            validateRequest(iotRequest);
            iotContext = initContext(version, iotRequest);
            handler.preHandle(iotRequest, iotResponse, iotContext);
            handler.process(iotRequest, iotResponse, iotContext);
            handler.postHandle(iotRequest, iotResponse, iotContext);

        } catch (IOTException e) {

            logger.warn("iot in exception", e);

            ResponseCodeHelper.parseErrorResponse(iotResponse, e);

        } catch (Exception e) {

            logger.warn("iot in exception", e);

            ResponseCodeHelper.parseErrorResponse(iotResponse, e);

        }

        logger.warn("[IOTResponse][{}][{}]", cmd, JsonUtil.toJson(iotResponse));

        return iotResponse;
    }

    private IOTResponse to400(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(400);
        return null;
    }

    public void validateRequest(IOTRequest iotRequest) throws IOTException {
        if (StringUtils.isEmpty(iotRequest.getMac()) || StringUtils.isEmpty(iotRequest.getSign())) {
            throw new IOTException(ResponseCodes.Sys.PARAM_ILLEGAL);
        }

        IOTDomain iotDomain = iotRequest.getDomain();
        iotDomain.validateDomain();
    }

    private IOTContext initContext(String version, IOTRequest iotRequest) throws IOTException {

        IOTContext iotContext = new IOTContext();

        Router router = routerService.getByMac(iotRequest.getMac());

        // 如果要忽略检查
        if (!iotRequest.routerCheckIgnore()) {

            if (router == null) {
                throw new IOTException(ResponseCodes.RouterErr.ROUTER_INFO_NOT_FOUND);
            }
        }

        iotContext.setRouter(router);

        iotContext.setVersion(Version.parse(version));

        return iotContext;

    }

    private BaseHandler getHandler(String version, String domain, String cmd) throws IOTException {
        try {
            BaseHandler baseHandler = ctx.getBean(domain + "_" + cmd + "_" + version, BaseHandler.class);
            return baseHandler;
        } catch (Exception e) {
            logger.warn("handler not found");
            return null;
        }
    }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
