package com.protops.gateway.interceptor;

import com.protops.gateway.constants.WeixinConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by damen on 2014/10/20.
 */
public class WeixinSecureInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WeixinSecureInterceptor.class);

    @Autowired
    private WeixinConfig weixinConfig;

    private List<String> excludeUrl;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(StringUtils.equalsIgnoreCase(request.getMethod(),"get")){
            return true;
        }

        // 签名
//        Signature signature = new Signature(request);
//
//        if (!WeixinUtil.checkSignature(signature)) {
//
//            logger.warn("signature failed;request:{}", signature.getSignature());
//
//            return false;
//
//        }
//
//        logger.warn("11231 {}", signature.toString());

        return true;
    }


    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public WeixinConfig getWeixinConfig() {
        return weixinConfig;
    }

    public void setWeixinConfig(WeixinConfig weixinConfig) {
        this.weixinConfig = weixinConfig;
    }

    public List getExcludeUrl() {
        return excludeUrl;
    }

    public void setExcludeUrl(List excludeUrl) {
        this.excludeUrl = excludeUrl;
    }
}
