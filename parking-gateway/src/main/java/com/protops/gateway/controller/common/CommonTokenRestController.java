package com.protops.gateway.controller.common;

import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.domain.AppInfo;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.common.TokenFactory;
import com.protops.gateway.service.AppInfoService;
import com.protops.gateway.service.common.TokenFactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jinx on 3/27/17.
 */
@Controller
public class CommonTokenRestController {

    private static final Logger logger = LoggerFactory.getLogger(CommonTokenRestController.class);

    @Autowired
    private TokenFactoryService tokenFactoryService;
    @Autowired
    private AppInfoService appInfoService;


    /**
     * 获取新的token
     *
     * @param appId
     * @param secret
     * @return
     */
    @RequestMapping(value = "rest/token")
    @ResponseBody
    public AjaxResponse getNewToken(@RequestParam(value = "appId", required = false) String appId,
                                    @RequestParam(value = "secret", required = false) String secret) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_APP_INFO_MSG, null);
        logger.warn("CommonTokenRestController.getNewToken [req:{}]", secret);
        AppInfo appInfo = appInfoService.check(appId, secret);
        if (appInfo != null) {
            TokenFactory tokenFactory = tokenFactoryService.findByBaseId(appId);
            Date date = tokenFactoryService.getTokenDate();
            if (tokenFactory != null) {
                if(tokenFactory.getTimestamp()>new Date().getTime()){
                    tokenFactory.setInvalidTime(date);
                    tokenFactory.setTimestamp(date.getTime());
                }else{
                    tokenFactory.setToken(UUID.randomUUID().toString());
                    tokenFactory.setInvalidTime(date);
                    tokenFactory.setTimestamp(date.getTime());
                }
            } else {
                tokenFactory = new TokenFactory();
                tokenFactory.setToken(UUID.randomUUID().toString());
                tokenFactory.setInvalidTime(date);
                tokenFactory.setType(1);
                tokenFactory.setBaseId(appId);
                tokenFactory.setTimestamp(date.getTime());
                tokenFactory.setCreateTime(new Date());
                tokenFactory.setSecret(secret);
            }
            tokenFactoryService.save(tokenFactory);
            ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, tokenFactoryService.findBaseToken(appId));
        }
        return ajaxResponse;
    }

}
