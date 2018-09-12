package com.protops.gateway.filter;

import com.protops.gateway.util.RandomIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoxiaoling on 7/24/14.
 */
public class SessionFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SessionFilter.class);


    private RandomIdentity randomIdentity = new RandomIdentity();


    /**
     * session 超时的时间，分钟
     */
    private int sessionTimeout = 15;

    /**
     * session id的缓存大小，实际不限制，影响清理缓存的频度
     */
    private int sessionMapSize = 50000;


    /**
     * 过滤不需要session的请求
     *
     * @param statelessUrls
     */
    public void setStatelessUrls(String statelessUrls) {
        String[] urls = statelessUrls.split(",");
        for (String url : urls) {
            url = url.trim();
            if (!url.isEmpty()) {
                this.statelessUrlList.add(url);
                logger.info("add url pattern {} to statelessUrlList", url);
            }
        }
    }

    private List<String> statelessUrlList = new ArrayList<String>();


    @Override
    protected void initFilterBean() throws ServletException {
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (statelessUrl(request)) {//无状态URL不需要检查sessionid
            return true;
        } else {
            return false;
        }
    }


    private AntPathMatcher matcher = new AntPathMatcher();


    private boolean statelessUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith(contextPath)) {
            requestURI = requestURI.substring(contextPath.length());
        }

        for (String patten : this.statelessUrlList) {
            if (matcher.match(patten, requestURI)) {
                logger.debug("url {} is stateless", requestURI);
                return true;
            }
        }

        return false;
    }


    private String generateNewSessionId(ServletRequest request) {
        return randomIdentity.next();
    }

    @Override
    public void destroy() {

    }

    public int getSessionMapSize() {
        return sessionMapSize;
    }

    public void setSessionMapSize(int sessionMapSize) {
        this.sessionMapSize = sessionMapSize;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}
