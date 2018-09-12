/**
 * 
 */
package com.protops.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ClassUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * NOTICE :
 * this filter must be the first filter to access request parameters
 *
 * @author Jacky
 * @see org.springframework.web.filter.CharacterEncodingFilter
 */
public class CharacterEncodingFilter extends OncePerRequestFilter { 
	
	private static final Logger logger = LoggerFactory.getLogger(CharacterEncodingFilter.class);
	
	private AntPathMatcher matcher = new AntPathMatcher();
	
	private List<String> forcedEncodingUrlList = new ArrayList<String>();
	
	public void setForcedEncodingUrls(String forcedEncodingUrls) {
		String[] urls = forcedEncodingUrls.split(",");
		for (String url : urls) {
			url = url.trim();
			if (!url.isEmpty()) {
				this.forcedEncodingUrlList.add(url);
				logger.info("Add URI pattern {} to ignored list", url);
			}
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		
		if (requestURI.startsWith(contextPath)) {
			requestURI = requestURI.substring(contextPath.length());
		}

		boolean forced = false;
		for (String ignoredURI : this.forcedEncodingUrlList) {
			if (matcher.match(ignoredURI, requestURI)) {
				logger.trace("URI {} matches configured pattern {} and ingored to set charset encoding", requestURI, ignoredURI);
                forced = true;
				break;
			}
		}

        //set request encoding
		if (forced) {
            request.setCharacterEncoding(this.forcedEncoding);
		}else{
            if (this.encoding != null && request.getCharacterEncoding() == null) {
                request.setCharacterEncoding(this.encoding);
            }
        }
        // Important: force it effective
        request.getParameter("name");

//        //set default response encoding
//        if (this.encoding != null
//                && request.getCharacterEncoding() == null
//                && responseSetCharacterEncodingAvailable) {
//            response.setCharacterEncoding(this.encoding);
//        }

		filterChain.doFilter(request, response);
	}


	//=============================================================================================
	// Below code copied from org.springframework.web.filter.CharacterEncodingFilter

	// Determine whether the Servlet 2.4 HttpServletResponse.setCharacterEncoding(String)
	// method is available, for use in the "doFilterInternal" implementation.
	private final static boolean responseSetCharacterEncodingAvailable = ClassUtils.hasMethod(
			HttpServletResponse.class, "setCharacterEncoding", new Class[] {String.class});


	private String encoding;

	private String forcedEncoding;


	/**
	 * Set the encoding to use for requests. This encoding will be passed into a
	 * {@link javax.servlet.http.HttpServletRequest#setCharacterEncoding} call.
	 * <p>Whether this encoding will override existing request encodings
	 * (and whether it will be applied as default response encoding as well)
	 *
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Set whether the configured {@link #setEncoding encoding} of this filter
	 * is supposed to override existing request and response encodings.
	 * <p>Default is "false", i.e. do not modify the encoding if
	 * {@link javax.servlet.http.HttpServletRequest#getCharacterEncoding()}
	 * returns a non-null value. Switch this to "true" to enforce the specified
	 * encoding in any case, applying it as default response encoding as well.
	 * <p>Note that the response encoding will only be set on Servlet 2.4+
	 * containers, since Servlet 2.3 did not provide a facility for setting
	 * a default response encoding.
	 */
	public void setForcedEncoding(String forcedEncoding) {
		this.forcedEncoding = forcedEncoding;
	}

}
