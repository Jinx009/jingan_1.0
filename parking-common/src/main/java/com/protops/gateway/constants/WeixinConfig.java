package com.protops.gateway.constants;

import com.protops.gateway.domain.SystemConfig;
import com.protops.gateway.util.FileUtil;
import com.protops.gateway.util.HttpUtil;
import com.protops.gateway.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by damen on 2014/10/26.
 */
public class WeixinConfig extends SystemConfig implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(WeixinConfig.class);

    public static final String SCOPE_USERINFO = "snsapi_userinfo";

    public static final String SCOPE_BASE = "snsapi_base";

    private static final String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

    private static final String menuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={0}";

    private static final String FILENAME = "weixinMenu.json";

    private static final String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";

    private String menuJson;

    private String accessToken;

    private Integer expiredIn;

    private String token;

    private String appId;

    private String appSecret;

    private String ticket;


    /*
    微信支付信息
     */
    private String merchantId;

    private String currencyCode;

    private String key;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getWeixinAccessTokenUrl() {
        return MessageFormat.format(url, appId, appSecret);
    }

    public String getWeixinMenuUrl() {
        return MessageFormat.format(menuUrl, accessToken);
    }

    public String getTemplateUrl() {
        return MessageFormat.format(TEMPLATE_URL, getAccessToken());
    }

    public boolean isExpired() {
        if (getAccessToken() != null) {
            return false;
        }
        return true;
    }

    public void reloadAccessToken() throws Exception {

        String url = getWeixinAccessTokenUrl();

        logger.warn("[accessTokenRequest:{}]", url);

        String result = HttpUtil.get(url, Constants.Default_SysEncoding);

        logger.warn("[accessTokenResponse:{}]", result);

        Map<String, Object> accessResponse = JsonUtil.fromJson(result, Map.class);

        setExpiredIn((Integer) accessResponse.get("expires_in"));
        setAccessToken((String) accessResponse.get("access_token"));

        String template = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";

        String ticketUrl = MessageFormat.format(template, getAccessToken());

        String ticketResult = HttpUtil.get(ticketUrl, Constants.Default_SysEncoding);

        Map<String, Object> ticketResponse = JsonUtil.fromJson(ticketResult, Map.class);

        setTicket((String) ticketResponse.get("ticket"));

        logger.warn("ticketResponse:{}", ticketResult);

        logger.warn("ticket:{}", getTicket());

    }

    public Integer getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(Integer expiredIn) {
        this.expiredIn = expiredIn;
    }

    public void reloadMenu() throws Exception {

        String url = getWeixinMenuUrl();

        logger.warn("menu url : {}", url);

        String result = HttpUtil.post(url, getMenuJson());

        logger.warn("here {}", result);
    }

    public void flush(String weixinMenu) {
        try {
            FileUtil.saveBytesAsFile(getFilePath() + FILENAME, weixinMenu.getBytes(Constants.Default_SysEncoding));
        } catch (UnsupportedEncodingException e) {

        }

        setMenuJson(weixinMenu);

    }

    public void afterPropertiesSet() throws Exception {

        loadFromFile();

    }

    public String getMenuJson() {
        return menuJson;
    }

    public void setMenuJson(String menuJson) {
        this.menuJson = menuJson;
    }

    public void loadFromFile() {
        setMenuJson(FileUtil.loadFileAsString(getFilePath() + FILENAME));
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
