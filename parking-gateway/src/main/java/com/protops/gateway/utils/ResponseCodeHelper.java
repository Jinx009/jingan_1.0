package com.protops.gateway.utils;

import com.protops.gateway.constants.CodeMapping;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.exception.GatewayException;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.weixin.util.ReplyUtil;
import com.protops.gateway.weixin.vo.push.Push;
import com.protops.gateway.weixin.vo.reply.Reply;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ResponseCodeHelper {

    private static String buildMsg(String msg, String code) {

        StringBuilder sb = new StringBuilder(msg);
        sb.append("[").append(code).append("]");

        return sb.toString();
    }

    public static String parseMessage(WeixinException e){

        String respCode = CodeMapping.getMappingCode(e.getResponseCode());

        return getMessage(respCode, e.getResponseCode(), e.getMessageKeys());

    }


    public static Reply parseMessage(Push push, WeixinException e) {

        String respCode = CodeMapping.getMappingCode(e.getResponseCode());

        String msg = getMessage(respCode, e.getResponseCode(), e.getMessageKeys());

        return ReplyUtil.buildTextReply(msg, push);
    }

    public static AjaxResponse parseErrorResponse(String responseCode, String ... messageKeys) {

        AjaxResponse response = new AjaxResponse();

        String respCode = CodeMapping.getMappingCode(responseCode);

        response.setRespCode(respCode);

        String msg = getMessage(respCode, responseCode, messageKeys);

        response.setMsg(msg);

        return response;
    }

    public static void parseErrorResponse(AjaxResponse response, WeixinException e) {

        String respCode = CodeMapping.getMappingCode(e.getResponseCode());

        response.setRespCode(respCode);

        String msg = getMessage(respCode, e.getResponseCode(), e.getMessageKeys());

        response.setMsg(msg);
    }

    public static void parseErrorResponse(AjaxResponse response, GatewayException e) {

        String respCode = CodeMapping.getMappingCode(e.getResponseCode());

        response.setRespCode(respCode);

        String msg = getMessage(respCode, e.getResponseCode(), e.getMessageKeys());

        response.setMsg(msg);
    }


    private static String getMessage(String mappedCode, String responseCode, Object... messageKeys) {

        String msg = getMsg(mappedCode);

        if (messageKeys != null) {
            msg = MessageFormat.format(msg, messageKeys);
        }

        return buildMsg(msg, responseCode);
    }


    private static final String getMsg(String mappedCode) {
        return ResourceBundle.getBundle("responsecodes").getString(mappedCode);
    }
}