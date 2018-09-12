package com.protops.gateway.util;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;


public class HttpHelper {


	private static final String SET_COOKIE_HEADER = "Set-Cookie";
	private static final String COOKIE_TPL_NEGATIVE = "{0}={1}; Version=1; Path={2}{3}; HttpOnly";
	private static final String COOKIE_TPL_ZERO = "{0}={1}; Expires=Thu, 01-Jan-1970 00:00:00 GMT; Path={2}{3}; HttpOnly";
	private static final String COOKIE_TPL_POSITIVE = "{0}={1}; Expires={2}; Path={3}{4}; HttpOnly";

	private static final String HTTP_HEADER_CDN_SRC_IP = "Cdn-Src-Ip";
	private static final String[] HTTP_HEADER_IP_ADDRESS = { "x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP" };
	private static final String[] HTTP_HEADER_IP_ADDRESS_ALL = (String[]) ArrayUtils.addAll(
			new String[] { HTTP_HEADER_CDN_SRC_IP }, HTTP_HEADER_IP_ADDRESS);

    // 向客户端写入Cookiede的Key常量
    public static final String CK_KEY_LOGIN_NAME = "_up_sun";

//    public static final String SESSION_ID = "up_session_id";

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static String getCdnSrcIp(HttpServletRequest request) {
		return (null == request) ?  null : request.getHeader(HTTP_HEADER_CDN_SRC_IP);
	}
	
	/**
	 * No CDN logic for IP address.
	 */
	public static String getNormalClientIp(HttpServletRequest request) {
		return getIpAddress(request, HTTP_HEADER_IP_ADDRESS);
	}

	public static String getClientIp(HttpServletRequest request) {
		return getIpAddress(request, HTTP_HEADER_IP_ADDRESS_ALL);
	}

    public static Map<String, String> getAllIps(HttpServletRequest request) {
        Map<String, String> ipMap = new LinkedHashMap<String, String>();
        
        for (String key : HTTP_HEADER_IP_ADDRESS_ALL) {
            String ip = request.getHeader(key);
            if (StringUtils.isNotBlank(ip)) {
                ipMap.put(key, ip);
            }
        }
        
        ipMap.put("RemoteAddr", request.getRemoteAddr());
        
        return ipMap;
    }

	private static String getIpAddress(HttpServletRequest request, String[] ipHeaders) {
		if (null == request || ipHeaders == null) {
			return null;
		}

		String ip = null;
		//src_key = null;

		for (String key : ipHeaders) {
			ip = request.getHeader(key);
			if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
				//src_key = key;
				break;
			}
		}
		// default
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			//src_key = "RemoteAddr";
		}

//		if (logger.isDebugEnabled()) {
//			logger.debug(formatMessage("ClientIP({0})={1}", src_key, ip));
//		}

		return ip == null ? "" : getFirstIp(ip.split(","));
	}

	private static String getFirstIp(String[] ips) {
		for (String ip : ips) {
			if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		return "";
	}

	public static String getHttpReferr(HttpServletRequest request) {
		return request.getHeader("referer");
	}

	public static boolean isPost(HttpServletRequest request) {
		return request != null && "post".equalsIgnoreCase(request.getMethod());
	}

	public static String getRequestValue(HttpServletRequest request, String key) {
		return request.getParameter(key);
	}

	/**
	 * 避免用这种方式解决乱码的问题, 一定是byte[] 到正确的String， String到正确的byte[]
	 * new String(s.getBytes(UpopConstant.SERVER_CHARSET), charset);
	 */
	
//	public static String getRequestValue(HttpServletRequest request, String key, String charset) {
//		String s = getRequestValue(request, key);
//		if (s != null) {
//			try {
//				s = new String(s.getBytes(UpopConstant.SERVER_CHARSET), charset);
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
//		return s;
//	}

	public static int getRequestValueAsInt(HttpServletRequest request, String key) {
		String s = getRequestValue(request, key);
		return Integer.parseInt(s);
	}

	public static long getRequestValueAsLong(HttpServletRequest request, String key) {
		String s = getRequestValue(request, key);
		return Long.parseLong(s);
	}

    /**
     * 从Cookie中取值
     *
     * @param request
     * @param key
     * @return
     */
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cks = request.getCookies();
        if (cks != null && cks.length > 0) {
            for (Cookie c : cks) {
                if (c.getName().equalsIgnoreCase(key)) {
                    return c.getValue();
                }
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * cookie path 都设置为 /
     * @param request
     * @param response
     * @param key
     * @param value
     * @param age
     * @param httpOnly
     */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String key, String value, int age,boolean httpOnly) {

        String contextPath = "/";

//        if(key.equals("UPOP_SESS_KEY")){      //为了防止和联合登陆一致性 老网关联合登陆接口放在顶级域名下面   补丁
//            contextPath = "/";
//        }else{
//            contextPath = request.getContextPath();
//            if (StringUtils.isBlank(contextPath)) {
//                contextPath = "/";
//            }
//        }


		//String contextPath = request.getContextPath();
//		if (StringUtils.isBlank(contextPath)) {
//			contextPath = "/";
//		}
		if (httpOnly) {

			final String secure;
			String cookieValue;
			final String cookie;

			secure = "";
//			if (request.isSecure()) {
//				secure = "; Secure";
//			} else {
//				secure = "";
//			}

			cookieValue = StringUtils.defaultIfEmpty(value, "");
			cookieValue = cookieValue.replaceAll("\"", "\\\\\"");
			if (cookieValue.contains("=") || cookieValue.contains("\\\"")) {
				cookieValue = "\"" + cookieValue + "\"";
			}

			if (age < 0) {
				cookie = StringUtils.formatMessage(COOKIE_TPL_NEGATIVE, key, cookieValue, contextPath, secure);
			} else if (age == 0) {
				cookie = StringUtils.formatMessage(COOKIE_TPL_ZERO, key, cookieValue, contextPath, secure);
			} else {
                String formatedAge = DateUtil.formatDate4Cookie(age);
				cookie = StringUtils.formatMessage(COOKIE_TPL_POSITIVE, key, cookieValue, formatedAge, contextPath, secure);
			}



			response.addHeader(SET_COOKIE_HEADER, cookie);

//            if(key.equals("UPOP_SESS_KEY")){      //为了防止和联合登陆一致性 老网关联合登陆接口放在顶级域名下面    删除子域名下面的cookie信息  补丁
//                response.addHeader(SET_COOKIE_HEADER, StringUtils.formatMessage(COOKIE_TPL_ZERO, key, cookieValue, request.getContextPath(), secure));
//            }

		} else {
			Cookie cookie = new Cookie(key, value);
			// cookie.setDomain("");
			cookie.setPath(contextPath);
//			cookie.setSecure(true);
			cookie.setMaxAge(age);
			//cookie.setVersion(1);
			response.addCookie(cookie);
		}
	}

	/**
	 * get web root url, which is: http(s)://domain.com:port/contextPath
	 * 
	 * @param request
	 * @return
	 */
	public static String getWebRootUrl(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append(request.getScheme()).append("://");
		sb.append(request.getServerName());
		if (!((request.isSecure() && request.getServerPort() == 443) || (!request.isSecure() && request.getServerPort() == 80))) {
			sb.append(":").append(request.getServerPort());
		}
		sb.append(request.getContextPath());
		return sb.toString();
	}

	public static String getRequestUriWithParameters(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getRequestURI());
		String queryString = request.getQueryString();
		if (StringUtils.isNotEmpty(queryString)) {
			sb.append("?").append(queryString);
		}
		return sb.toString();
	}
	
	/**
     * url解码
     *
     * @param str
     * @return
     */
    public static String decodeUrl(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String decodeStr = null;
        try {
            decodeStr = URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        return decodeStr;
    }

    /**
     * url编码
     *
     * @param str
     * @return
     */
    public static String encodeUrl(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String encodeStr = null;
        try {
            encodeStr = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        return encodeStr;
    }


    public static String getHttpReferer(HttpServletRequest request) {
        return request.getHeader("referer");
    }



    private static final String COLON_DOUBLE_SLASH = "://";
    private static final String SLASH = "/";
    private static final String COLON = ":";
    private static final String WWW_DOT = "www.";
    private static final String DOT = ".";
    private static final String DOMAIN_DELIMITER = "|";
    private static final String REGEX_DOMAIN_DELIMITER = "\\" + DOMAIN_DELIMITER;

    /**
     * Get domain name without www.
     * E.g., url: http://www.unionpay.com:8080/pay/payuahead.action
     * the returned string should be: unionpay.com
     *
     * @param url
     * @return
     */
    public static String getDomainNameWithout3W(String url) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(url)) {
            String domainNameWithout3W = url;
            if (url.startsWith(DOT)) {
                // remove first start dot
                domainNameWithout3W = url.substring(1);
            }

            if (url.contains(COLON_DOUBLE_SLASH)) {
                // remove *://, like http://
                domainNameWithout3W = url.substring(url.indexOf(COLON_DOUBLE_SLASH) + COLON_DOUBLE_SLASH.length());
            }

            if (domainNameWithout3W.contains(SLASH)) {
                // remove content after /.
                domainNameWithout3W = domainNameWithout3W.substring(0, domainNameWithout3W.indexOf(SLASH));
            }

            if (domainNameWithout3W.contains(COLON)) {
                // remove port
                domainNameWithout3W = domainNameWithout3W.substring(0, domainNameWithout3W.indexOf(COLON));
            }

            if (domainNameWithout3W.contains(WWW_DOT)) {
                // remove www.
                domainNameWithout3W = domainNameWithout3W.substring(domainNameWithout3W.indexOf(WWW_DOT) + WWW_DOT.length());
            }

            return domainNameWithout3W;
        }

        return null;
    }

}
