package com.protops.gateway.handler.v1_0;

import com.protops.gateway.constants.Constants;
import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.domain.SignInRequest;
import com.protops.gateway.domain.SignInResponse;
import com.protops.gateway.domain.base.IOTRequest;
import com.protops.gateway.domain.base.IOTResponse;
import com.protops.gateway.domain.iot.Router;
import com.protops.gateway.exception.IOTException;
import com.protops.gateway.handler.BaseHandler;
import com.protops.gateway.service.RouterService;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.util.StringUtils;
import com.protops.gateway.utils.CipherUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by damen on 2016/3/23.
 * 1，查看router是否存在数据库
 * 2，生成新的密钥
 * 3，如果不在数据库，生成新的数据，在数据库，更新数据
 * 4，使用原来的密钥来加密secret，并且返回前端
 */
public class SignInHandler extends BaseHandler<SignInRequest, SignInResponse> {

    @Autowired
    RouterService routerService;

    @Override
    public void doPreHandle(IOTRequest<SignInRequest> iotRequest, IOTResponse<SignInResponse> iotResponse, IOTContext iotContext) throws IOTException {


    }

    @Override
    public void doPostHandle(IOTRequest<SignInRequest> iotRequest, IOTResponse<SignInResponse> iotResponse, IOTContext iotContext) throws IOTException {

    }

    @Override
    public void process(IOTRequest<SignInRequest> request, IOTResponse<SignInResponse> response, IOTContext iotContext) throws IOTException {

        SignInResponse signInResponse = new SignInResponse();

        // 该接口用于生成新的secret
        Router router = iotContext.getRouter();

        String previousSecret = iotContext.getSecret();

        String newSecret = genSecret();

        if (router == null) {
            router = new Router();
        }

        SignInRequest signInRequest = request.getDomain();

        router.setSecret(newSecret);
        router.setPanId(signInRequest.getPanId());
        router.setFirmwareVersion(signInRequest.getFirmwareVersion());
        router.setFrequency(signInRequest.getFrequency());
        router.setChannelId(signInRequest.getChannelId());
        router.setHeartbeatInterval(signInRequest.getHeartbeatInterval());
        router.setInterfaceVersion(signInRequest.getInterfaceVersion());
        router.setMac(signInRequest.getMac());
        router.setStatus(0);

        // 使用旧的secret来加密报文
        String encryptedSecret = encrypt(newSecret, previousSecret);

        routerService.save(router);

        router.setSecret(previousSecret);

        iotContext.setRouter(router);

        signInResponse.setEncryptedSecret(encryptedSecret);

        response.setDomain(signInResponse);

    }

    private String genSecret() {
        return RandomStringUtils.random(16, true, true);
    }

    private String encrypt(String body, String secret) throws IOTException {
        try {
            return StringUtils.toHexString(CipherUtil.encrypt(secret.getBytes(Constants.Default_SysEncoding), body.getBytes(Constants.Default_SysEncoding)));
        } catch (Exception e) {
            throw new IOTException(ResponseCodes.Sys.PARAM_ILLEGAL);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new String(CipherUtil.decrypt("MTJnqYLoZkjwVHA7".getBytes("utf-8"), StringUtils.hexStr2ByteArr("e73b7a8428b81c8aff29f1859b79b2c0")), "utf-8"));
    }


    @Override
    public IOTRequest<SignInRequest> parseRequest(byte[] requestBody) {
        return JsonUtil.fromJsonWithCustomObject(requestBody, new TypeReference<IOTRequest<SignInRequest>>() {});
    }
}
