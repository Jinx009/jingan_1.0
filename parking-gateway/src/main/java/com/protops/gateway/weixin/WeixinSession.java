package com.protops.gateway.weixin;

import com.protops.gateway.constants.Constants;
import com.protops.gateway.constants.WeixinConfig;
import com.protops.gateway.domain.Member;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by damen on 2014/11/1.
 */
public class WeixinSession {

    private static final Logger logger = LoggerFactory.getLogger(WeixinSession.class);

    private static final String WEIXIN_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=code&scope={2}&state=STATE#wechat_redirect";

    private static final String ACCESS_TOKEN_FOR_OAUTH = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";

    private static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";

    private static final String KEY_WEIXIN_SESSION = "weixinSession";

    public static final String CODE = "code";

    private String scope;

    private String openId;

    private String accessToken;

    private String httpSessionId;

    private Map<String, Object> params = new HashMap<String, Object>();

    private Member member;

    private String headImgUrl;

    public WeixinSession(String scope) {
        this.scope = scope;
    }

    public WeixinSession() {
    }

    public static WeixinSession get(HttpSession session) {
        return (WeixinSession) session.getAttribute(KEY_WEIXIN_SESSION);
    }

    public String getWeixinAuthUrl(String redirectUrl, WeixinConfig weixinConfig) {
        logger.warn("redirectAuthUrl : {}", redirectUrl);

        String url = null;
        try {
            url = MessageFormat.format(WEIXIN_URL, weixinConfig.getAppId(), URLEncoder.encode(redirectUrl, Constants.Default_SysEncoding), getScope());
        } catch (UnsupportedEncodingException e) {
            // ignored
        }

        return url;
    }

    public String redirectToAuth(String redirectUrl, WeixinConfig weixinConfig) {
        logger.warn("redirectAuthUrl : {}", redirectUrl);

        String url = null;
        try {
            url = MessageFormat.format(WEIXIN_URL, weixinConfig.getAppId(), URLEncoder.encode(redirectUrl, Constants.Default_SysEncoding), getScope());
        } catch (UnsupportedEncodingException e) {
            // ignored
        }

        return "redirect:" + url;
    }

    private Map<String, Object> getOAuthAccessToken(String weixinCode, WeixinConfig weixinConfig) throws Exception {

        String url = MessageFormat.format(ACCESS_TOKEN_FOR_OAUTH, weixinConfig.getAppId(), weixinConfig.getAppSecret(), weixinCode);

        String result = HttpUtil.get(url, Constants.Default_SysEncoding);

        logger.warn("access token : {}", result);

        Map<String, Object> retMap = JsonUtil.fromJson(result, Map.class);

        return retMap;
    }

    public void doAuth(String weixinCode, WeixinConfig weixinConfig) throws Exception {

        Map<String, Object> weixinOAuth = getOAuthAccessToken(weixinCode, weixinConfig);

        String accessTokenForOAuth = (String) weixinOAuth.get("access_token");
        if (StringUtils.isEmpty(accessTokenForOAuth)) {
            return;
        }
        setAccessToken(accessTokenForOAuth);

        String openId = (String) weixinOAuth.get("openid");
        if (StringUtils.isEmpty(openId)) {
            return;
        }
        setOpenId(openId);

        if (getScope().equals(WeixinConfig.SCOPE_BASE)) {
            return;
        }

        String url = MessageFormat.format(GET_USER_INFO_URL, accessTokenForOAuth, openId);

        String result = HttpUtil.get(url, Constants.Default_SysEncoding);

        Map<String, Object> userInfo = JsonUtil.fromJson(result, Map.class);

        String headImgUrl = (String) userInfo.get("headimgurl");

        setHeadImgUrl(headImgUrl);

        logger.warn("user info : {}", result);

    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void intoSession(HttpSession session) {
        session.setAttribute(KEY_WEIXIN_SESSION, this);
        setHttpSessionId(session.getId());
    }

    public void clear(HttpSession session) {
        session.removeAttribute(KEY_WEIXIN_SESSION);
    }

    public String getHttpSessionId() {
        return httpSessionId;
    }

    public void setHttpSessionId(String httpSessionId) {
        this.httpSessionId = httpSessionId;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void put(String key, Object value) {
        params.put(key, value);
    }

    public Object get(String key) {
        return params.get(key);
    }

    public void remove(String key) {
        params.remove(key);
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
}
