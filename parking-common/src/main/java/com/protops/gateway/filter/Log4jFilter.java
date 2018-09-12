package com.protops.gateway.filter;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Customized Log4j input parameters.
 * Usage example: in log4j configuration files, use [%X{paramName}] in ConversionPattern settings.
 *
 * @author gaoxiaoling
 */
public class Log4jFilter implements Filter {

    boolean customSessionId = false;

    public void init(FilterConfig filterConfig) throws ServletException {
        customSessionId = Boolean.valueOf(filterConfig.getInitParameter("customSessionId"));
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        try {
            if (HttpServletRequest.class.isInstance(request)) {
                HttpServletRequest req = (HttpServletRequest) request;

                MDC.put("SID", HttpServletRequest.class.cast(request).getSession().getId());

                chain.doFilter(request, response);
            }
        } finally {
            MDC.clear();
        }

    }

    public void destroy() {
        MDC.clear();
    }

    public boolean isCustomSessionId() {
        return customSessionId;
    }

    public void setCustomSessionId(boolean customSessionId) {
        this.customSessionId = customSessionId;
    }
}
