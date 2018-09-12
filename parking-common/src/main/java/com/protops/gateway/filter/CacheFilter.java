package com.protops.gateway.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class CacheFilter implements Filter {
	
	FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		for (Enumeration<String> e = filterConfig.getInitParameterNames(); e.hasMoreElements();) {
			String headerName = e.nextElement();
			res.addHeader(headerName, filterConfig.getInitParameter(headerName));
		}
		chain.doFilter(request, res);
	}

	public void destroy() {
		this.filterConfig = null;
	}
}
