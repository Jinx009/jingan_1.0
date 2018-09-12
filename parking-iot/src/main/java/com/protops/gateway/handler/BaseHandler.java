package com.protops.gateway.handler;

import com.protops.gateway.constants.Constants;
import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.domain.base.IOTDomain;
import com.protops.gateway.domain.base.IOTRequest;
import com.protops.gateway.domain.base.IOTResponse;
import com.protops.gateway.domain.iot.Router;
import com.protops.gateway.exception.IOTException;
import com.protops.gateway.util.SignUtils;
import com.protops.gateway.utils.CipherUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by damen on 2016/3/23.
 */
public abstract class BaseHandler<F extends IOTDomain, B extends IOTDomain> {

    private static final Logger logger = LoggerFactory.getLogger(BaseHandler.class);

    public abstract void doPreHandle(IOTRequest<F> iotRequest, IOTResponse<B> iotResponse, IOTContext iotContext) throws IOTException;

    public abstract void doPostHandle(IOTRequest<F> iotRequest, IOTResponse<B> iotResponse, IOTContext iotContext) throws IOTException;

    public abstract void process(IOTRequest<F> request, IOTResponse<B> response, IOTContext iotContext) throws IOTException;

    public void preHandle(IOTRequest<F> iotRequest, IOTResponse<B> iotResponse, IOTContext iotContext) throws IOTException {
        doAuth(iotRequest, iotContext);

        doPreHandle(iotRequest, iotResponse, iotContext);

    }


    public abstract IOTRequest<F> parseRequest(byte[] requestBody);

    // 对应答加密
    public void postHandle(IOTRequest iotRequest, IOTResponse iotResponse, IOTContext iotContext) throws IOTException {

        doPostHandle(iotRequest, iotResponse, iotContext);

        sign(iotRequest, iotResponse, iotContext);

    }

    private void sign(IOTRequest iotRequest, IOTResponse iotResponse, IOTContext iotContext) throws IOTException {

        Router router = iotContext.getRouter();

        IOTDomain iotDomain = iotResponse.getDomain();

        String signStr = StringUtils.EMPTY;
        if(iotDomain != null){
            signStr = iotDomain.parseSignStr(iotContext);
        }

        if (StringUtils.isBlank(signStr)) {
            signStr = "mac=" + iotRequest.getMac();
        } else {
            signStr = signStr + "&mac=" + iotRequest.getMac();
        }

        String ret = SignUtils.sign(signStr);

        try {

            ret = com.protops.gateway.util.StringUtils.toHexString(CipherUtil.encrypt(router.getSecret().getBytes(Constants.Default_SysEncoding), ret.getBytes(Constants.Default_SysEncoding)));

        } catch (Exception e) {

            throw new IOTException(ResponseCodes.Sys.AUTH_FAILED);

        }

        iotResponse.setSign(ret);
    }

    private void doAuth(IOTRequest iotRequest, IOTContext iotContext) throws IOTException {
        // 解密
        String decryptedSign = decrypt(iotRequest.getSign(), iotContext.getSecret());
        if (StringUtils.isBlank(decryptedSign)) {
            throw new IOTException(ResponseCodes.Sys.AUTH_FAILED);
        }

        IOTDomain iotDomain = iotRequest.getDomain();
        String sign = iotDomain.parseSignStr(iotContext);

        if (StringUtils.isBlank(sign)) {
            sign = "mac=" + iotRequest.getMac();
        } else {
            sign = sign + "&mac=" + iotRequest.getMac();
        }

        boolean ok = SignUtils.verify(sign, decryptedSign);

        if (!ok) {
            logger.error("加密前:{},加密后:{}",sign,decryptedSign);
            throw new IOTException(ResponseCodes.Sys.AUTH_FAILED);
        }

    }

    protected String decrypt(String body, String appSecret) throws IOTException {
        if (StringUtils.isEmpty(appSecret)) {
            return null;
        }

        String md5Sign = null;

        try {

            byte[] result = com.protops.gateway.util.StringUtils.hexStr2ByteArr(body);

            md5Sign = new String(CipherUtil.decrypt(appSecret.getBytes(Constants.Default_SysEncoding), result), Constants.Default_SysEncoding);

        } catch (Exception e) {

            throw new IOTException(ResponseCodes.Sys.AUTH_FAILED);

        }

        return md5Sign;
    }

}
