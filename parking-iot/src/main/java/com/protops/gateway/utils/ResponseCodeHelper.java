package com.protops.gateway.utils;

import com.protops.gateway.constants.CodeMapping;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.base.IOTResponse;
import com.protops.gateway.exception.IOTException;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ResponseCodeHelper {

    private static String buildMsg(String msg, String code) {

        StringBuilder sb = new StringBuilder(msg);
        sb.append("[").append(code).append("]");

        return sb.toString();
    }

    public static void parseErrorResponse(IOTResponse response, Exception e) {

        String respCode = CodeMapping.getMappingCode(ResponseCodes.Sys.SYS_ERR);

        response.setRespCode(respCode);

        String msg = getMessage(respCode, ResponseCodes.Sys.SYS_ERR);

        response.setResponseMsg(msg);

    }

    public static void parseErrorResponse(IOTResponse response, IOTException e) {

        String respCode = CodeMapping.getMappingCode(e.getResponseCode());

        response.setRespCode(respCode);

        String msg = getMessage(respCode, e.getResponseCode(), e.getMessageKeys());

        response.setResponseMsg(msg);

    }


    public static void parseErrorResponse(AjaxResponse response, IOTException e) {

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