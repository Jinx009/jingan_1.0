package com.protops.gateway.controller.weixin;

import com.protops.gateway.weixin.factory.processor.MessageProcessor;
import com.protops.gateway.weixin.util.WeixinUtil;
import com.protops.gateway.weixin.vo.Signature;
import com.protops.gateway.weixin.vo.reply.Reply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by damen on 2014/10/26.
 */
@Controller
@RequestMapping(value = "weixin/entry")
public class WeixinController {

    private static final Logger logger = LoggerFactory.getLogger(WeixinController.class);

    @RequestMapping(value = "process", method = RequestMethod.GET)
    @ResponseBody
    public String handleSignIn(HttpServletRequest request) {

        try {

            Signature signature = new Signature(request);

            logger.warn("signature:{}", signature.toString());

            boolean hasRights = WeixinUtil.checkSignature(signature);

            if (!hasRights) {
                logger.warn("signature-failed:{}", signature);
            }

            logger.warn("weixin signIn success");

            return signature.getEchostr();

        } catch (Exception e) {
            logger.error("signature-error", e);
        }

        return null;
    }

    @RequestMapping(value = "process", method = RequestMethod.POST, produces = "application/xml;charset=UTF-8")
    @ResponseBody
    public Reply handleWeixin(@RequestBody String message) {

        try {

            logger.warn("request message : {}", message);

            return MessageProcessor.getMessageProcessor(message).process();

        } catch (Exception e) {

            logger.error("unhandled exception", e);

        }

        return null;
    }


}
