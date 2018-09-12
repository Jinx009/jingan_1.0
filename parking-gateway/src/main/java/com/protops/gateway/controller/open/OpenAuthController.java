package com.protops.gateway.controller.open;

import com.protops.gateway.constants.AdminConfig;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.domain.AccessToken;
import com.protops.gateway.domain.AppInfo;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.exception.GatewayException;
import com.protops.gateway.service.AppInfoService;
import com.protops.gateway.service.AuthTokenService;
import com.protops.gateway.util.SignUtils;
import com.protops.gateway.utils.ResponseCodeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Created by damen on 2016/5/17.
 */
@Controller
public class OpenAuthController {

    @Autowired
    AdminConfig adminConfig;

    @Autowired
    AuthTokenService authTokenService;

    @Autowired
    AppInfoService appInfoService;

    private static final String ACCESSTOKEN_SIGN_TEMPLATE = "appId={0}&appSecret={1}&timestamp={2}";

    /**
     * 1，根据appId获取appInfo
     * 2，验签
     * 3，根据appInfo获取接口调用的权限和频率
     * 4，
     *
     * @param appId
     * @param timestamp
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "auth/getAccessToken", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<String> getAccessToken(@RequestParam("sign") String sign, @RequestParam("appId") String appId, @RequestParam("timestamp") String timestamp) throws IOException, GatewayException {

        AjaxResponse ajaxResponse = new AjaxResponse();

        try {

            AppInfo appInfo = appInfoService.getByAppId(appId);

            if (appInfo == null) {
                throw new GatewayException(ResponseCodes.Sys.PARAM_ILLEGAL);
            }

            if (!adminConfig.isOnTestMode()) {

                String appSecret = appInfo.getAppSecret();

                String verifySign = MessageFormat.format(ACCESSTOKEN_SIGN_TEMPLATE, appId, appSecret, timestamp);

                boolean ok = SignUtils.verify(verifySign, sign);

                if (!ok) {
                    throw new GatewayException(ResponseCodes.Sys.PARAM_ILLEGAL);
                }

            }

            AccessToken accessToken = authTokenService.generate(appInfo);

            ajaxResponse.setDomain(accessToken);

        } catch (GatewayException e) {

            ResponseCodeHelper.parseErrorResponse(ajaxResponse, e);

        }
        return ajaxResponse;
    }

}
