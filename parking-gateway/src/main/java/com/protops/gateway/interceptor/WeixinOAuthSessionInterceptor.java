package com.protops.gateway.interceptor;

import com.protops.gateway.constants.AdminConfig;
import com.protops.gateway.constants.WeixinConfig;
import com.protops.gateway.domain.Member;
import com.protops.gateway.service.weixin.MemberService;
import com.protops.gateway.weixin.WeixinSession;
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
 * Created by damen on 2014/11/2.
 */
public class WeixinOAuthSessionInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WeixinSecureInterceptor.class);

    private String path;

    private List<String> excludeUrl;

    private List<String> memberRequireExcludeUrl;

    @Autowired
    private WeixinConfig weixinConfig;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    MemberService memberService;

    private String getRedirectUrl(String requestUri) {

        String contextpath = adminConfig.getContextPath();

        String serviceName = requestUri.substring(requestUri.lastIndexOf("/"));

        return contextpath + getPath() + serviceName;

    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.warn("[request][{}][{}]", request.getRequestURL().toString(), request.getSession().getId());

//        // post 方法不验证
//        if(StringUtils.equalsIgnoreCase("post", request.getMethod())){
//            return true;
//        }

        String requestUri = request.getRequestURI();
        String serviceName = requestUri.substring(requestUri.lastIndexOf("/"));

        for (String uri : getExcludeUrl()) {

            if (uri.equals(serviceName)) {
                return true;
            }
        }

        WeixinSession weixinSession = WeixinSession.get(request.getSession());

        // 如果缓存中没有weixinSession，创建session
        if (weixinSession == null) {

            logger.warn("[{}][weixinSession is null, direct to weixin server to auth]", request.getSession().getId());

            weixinSession = new WeixinSession(WeixinConfig.SCOPE_USERINFO);

            weixinSession.intoSession(request.getSession());

            String redirectUrl = getRedirectUrl(request.getRequestURI());

            logger.warn("redirect_url : {}", redirectUrl);

            response.sendRedirect(weixinSession.getWeixinAuthUrl(redirectUrl, weixinConfig));

            return false;

        } else {

            String weixinCode = request.getParameter(WeixinSession.CODE);

            // 如果code不空
            if (!StringUtils.isEmpty(weixinCode)) {

                logger.warn("[{}][weixinCode is not empty, do auth]", request.getSession().getId());

                weixinSession.doAuth(weixinCode, weixinConfig);

            }

            // 发起认证之后，如果weixinSession中的
            if (StringUtils.isEmpty(weixinSession.getOpenId())) {

                weixinSession.clear(request.getSession());

                response.sendRedirect(adminConfig.getContextPath() + "/404");

                return false;
//                return true;
            }

        }

        for (String memberRequiredExcludeUrl : getMemberRequireExcludeUrl()) {

            if (memberRequiredExcludeUrl.equals(serviceName)) {
                logger.warn("skip:{}", serviceName);
                return true;
            }

        }

        if (weixinSession.getMember() == null) {

            Member member = memberService.getByWeixinId(weixinSession.getOpenId());

            if (member == null) {

                response.sendRedirect(adminConfig.getContextPath() + "/weixin/h5/register");

                return false;

            }

            weixinSession.setMember(member);
        }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getExcludeUrl() {
        return excludeUrl;
    }

    public void setExcludeUrl(List<String> excludeUrl) {
        this.excludeUrl = excludeUrl;
    }

    public List<String> getMemberRequireExcludeUrl() {
        return memberRequireExcludeUrl;
    }

    public void setMemberRequireExcludeUrl(List<String> memberRequireExcludeUrl) {
        this.memberRequireExcludeUrl = memberRequireExcludeUrl;
    }
}
