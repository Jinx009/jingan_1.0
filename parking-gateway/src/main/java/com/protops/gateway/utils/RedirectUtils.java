package com.protops.gateway.utils;

import com.protops.gateway.constants.AdminConfig;
import com.protops.gateway.weixin.WeixinSession;

/**
 * Created by zhouzhihao on 2014/10/15.
 */
public class RedirectUtils {

    public static final String toGlobalErr(WeixinSession weixinSession, String key) {
        return "redirect:" + AdminConfig.getStaticContextPath() + "/weixin/h5/error";
    }

//    public static final String addRedirectAttributes(String page, RedirectAttributes redirectAttributes, String key) {
//        String message = ResponseCodes.getMsg(key);
//        redirectAttributes.addFlashAttribute("err-msg", message);
//        return "redirect:" + page;
//    }
//
//    public static final String addRedirectAttributes(String page, RedirectAttributes redirectAttributes, AdminException e){
//        String message = ResponseCodesHelper.buildErrResponse(e);
//        redirectAttributes.addFlashAttribute("err-msg", message);
//        return "redirect:" + page;
//    }

}
