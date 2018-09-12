package com.protops.gateway.controller.weixin;

import com.protops.gateway.constants.AdminConfig;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.util.StringUtils;
import com.protops.gateway.utils.AjaxUtils;
import com.protops.gateway.weixin.WeixinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by zhouzhihao on 2015/5/8.
 */
public class WeixinBaseController {

    private static final Logger logger = LoggerFactory.getLogger(WeixinBaseController.class);

    @Autowired
    AdminConfig adminConfig;

    @ModelAttribute("weixinSession")
    public WeixinSession getWeixinSession(HttpSession session, HttpServletRequest request, Model model) throws WeixinException, IOException {

        String requestUrl = request.getRequestURL().toString();

        String curUri = requestUrl.replace(adminConfig.getContextPath() + "/weixin/h5/", "");

        model.addAttribute("curUri", curUri);

        return WeixinSession.get(session);

    }


    @ExceptionHandler
    public ModelAndView exp(HttpServletRequest request, HttpServletResponse response, Exception ex) {

        if (AjaxUtils.isAjaxRequest(request)) {
            logger.warn("errorInExp", ex);
            AjaxUtils.responseClient(response, ResponseCodes.Sys.SYS_ERR);
            return null;
        }

        ModelAndView modelAndView = new ModelAndView("error");

        WeixinSession weixinSession = WeixinSession.get(request.getSession());

        String openId = StringUtils.EMPTY;
        if (weixinSession == null) {
            openId = weixinSession.getOpenId();
        }

        logger.warn("errorInExp", ex);

        logger.warn("[openId={}][currentUri={}][queryString={}]", new Object[]{openId, request.getRequestURL().toString(), request.getQueryString()});
        logger.warn("error", ex);

        return modelAndView;
    }

}
