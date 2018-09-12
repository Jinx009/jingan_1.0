package com.protops.gateway.interceptor;

import com.protops.gateway.constants.Constants;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.domain.AccessToken;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.exception.AccessCountExceedException;
import com.protops.gateway.exception.AccessTimeoutException;
import com.protops.gateway.service.AuthTokenService;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.utils.ResponseCodeHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by fanlin on 15/4/18.
 */
public class AccessTokenInterceptor implements HandlerInterceptor {

    private List<String> white;

    @Autowired
    AuthTokenService authTokenService;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        boolean ok = true;

        String accessTokenStr = httpServletRequest.getParameter(Constants.KEY_ACCESS_TOKEN);

        try {

            for(String url :white){
                if(httpServletRequest.getRequestURL().toString().contains(url)){
                    return true;
                }
            }

            if(StringUtils.isBlank(accessTokenStr)){
                return error(httpServletResponse, ResponseCodes.Sys.AUTH_FAILED);
            }

            AccessToken accessToken = authTokenService.getAccessToken(accessTokenStr);

            if (accessToken == null) {
                return error(httpServletResponse, ResponseCodes.Sys.AUTH_FAILED);
            }

        } catch (AccessTimeoutException e) {

            return error(httpServletResponse, ResponseCodes.Sys.AUTH_TIME_EXPIRED);


        } catch (AccessCountExceedException e) {

            return error(httpServletResponse, ResponseCodes.Sys.AUTH_COUNT_EXCEED);

        }

        // 后续进行权限判断TODO

        return ok;
    }

    private boolean error(HttpServletResponse httpServletResponse, String respCode, String... messageKeys) throws IOException {
        AjaxResponse ajaxResponse = ResponseCodeHelper.parseErrorResponse(respCode, messageKeys);
        responseClient(httpServletResponse, ajaxResponse);
        return false;
    }


    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void responseClient(HttpServletResponse response, AjaxResponse ajaxResponse) {
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

    public List<String> getWhite() {
        return white;
    }

    public void setWhite(List<String> white) {
        this.white = white;
    }
}
