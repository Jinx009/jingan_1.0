package com.protops.gateway.weixin.factory.processor;

import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.weixin.handler.Handler;
import com.protops.gateway.weixin.vo.push.Push;

/**
 * @author zzh
 */
public class ImageProcessor extends MessageProcessor {

    public ImageProcessor(String message, Class<? extends Push> pushClass) {
        super(message, pushClass);
    }

    @Override
    public Handler getEventHandler() throws WeixinException {
        return null;
    }


}
