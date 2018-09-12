/**
 * 版权所有(C)，中国银联股份有限公司，2002-2014，所有权利保留。
 * <p>
 * 项目名：	web-pc
 * 文件名：	AjaxUtils.java
 * 模块说明：
 * 修改历史：
 * 2014-5-28 - linhui - 创建。
 */
package com.protops.gateway.utils;

import com.protops.gateway.constants.Constants;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author linhui
 *
 */
public class AjaxUtils {
    public static boolean isAjaxRequest(HttpServletRequest webRequest) {
        String requestedWith = webRequest.getHeader("X-Requested-With");
        return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
    }

    public static boolean isAjaxUploadRequest(HttpServletRequest webRequest) {
        return webRequest.getParameter("ajaxUpload") != null;
    }

    public static void responseClient(HttpServletResponse response, String key) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ResponseCodeHelper.parseErrorResponse(ajaxResponse, new WeixinException(key));
        responseClient(response, ajaxResponse);
    }

    public static void responseClient(HttpServletResponse response, AjaxResponse ajaxResponse) {
        if (response == null) {
            return;
        }

        PrintWriter pw = null;
        try {
            response.setCharacterEncoding(Constants.Default_SysEncoding);
            response.setContentType("application/json;charset=UTF-8");
            pw = response.getWriter();
            pw.write(JsonUtil.toJson(ajaxResponse));
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }

}
