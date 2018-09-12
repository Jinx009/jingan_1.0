package com.protops.gateway.weixin.factory.processor;


import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.exception.ProtopsException;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.utils.ResponseCodeHelper;
import com.protops.gateway.weixin.factory.PushProcessorFactory;
import com.protops.gateway.weixin.handler.Handler;
import com.protops.gateway.weixin.util.WeixinUtil;
import com.protops.gateway.weixin.util.XmlUtil;
import com.protops.gateway.weixin.vo.push.Push;
import com.protops.gateway.weixin.vo.reply.Reply;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zzh
 */
public abstract class MessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    protected Push push;

    public MessageProcessor(String message, Class<? extends Push> pushClass) {
        this.push = XmlUtil.unmarshal(message, pushClass);
    }

    public Reply process() {

        try {

            return getEventHandler().handleEvent(push);

        } catch (WeixinException e) {

            Reply reply = ResponseCodeHelper.parseMessage(push, e);

            logger.warn(JsonUtil.toJson(reply));

            // 如果是无服务，直接出去
            if(ResponseCodes.Weixin.NO_SUCH_SERVICE.equals(e.getResponseCode())){
                return null;
            }

            return reply;

        }
    }

    public static MessageProcessor getMessageProcessor(String message) throws ProtopsException {

        String messageType = WeixinUtil.getMsgType(message);

        if (StringUtils.isEmpty(messageType)) {
            throw new ProtopsException(ResponseCodes.Weixin.NO_SUCH_SERVICE);
        }

        // text or event
        PushProcessorFactory pushEnumFactory = EnumUtils.getEnum(PushProcessorFactory.class, StringUtils.upperCase(messageType));

        if (pushEnumFactory == null) {
            throw new ProtopsException(ResponseCodes.Weixin.NO_SUCH_SERVICE);
        }

        // 每次创建一个新的parser来处理，parse包含一个push对象
        MessageProcessor processor = pushEnumFactory.createProcessor(message);

        return processor;

    }

    public abstract Handler getEventHandler() throws WeixinException;

}
