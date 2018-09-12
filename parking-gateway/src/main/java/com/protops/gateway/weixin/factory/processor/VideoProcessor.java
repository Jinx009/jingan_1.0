package com.protops.gateway.weixin.factory.processor;

import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.weixin.handler.Handler;
import com.protops.gateway.weixin.vo.push.Push;
import com.protops.gateway.weixin.vo.reply.Reply;

/**
 * @author zzh
 */
public class VideoProcessor extends MessageProcessor {

    public VideoProcessor(String message, Class<? extends Push> pushClass) {
        super(message, pushClass);
    }

    public Reply process() {
        return null;
    }

    @Override
    public Handler getEventHandler() throws WeixinException {
        return null;
    }
}
