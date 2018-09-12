package com.protops.gateway.weixin.factory.processor;


import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.constants.enums.TextKeyEnum;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.utils.ServiceStaticAccess;
import com.protops.gateway.weixin.handler.Handler;
import com.protops.gateway.weixin.vo.push.Push;
import com.protops.gateway.weixin.vo.push.TextPush;
import org.apache.commons.lang3.EnumUtils;

/**
 * @author zzh
 */
public class TextProcessor extends MessageProcessor {

    public TextProcessor(String message, Class<? extends Push> pushClass) {
        super(message, pushClass);
    }


    @Override
    public Handler getEventHandler() throws WeixinException {

        TextPush textPush = (TextPush) push;

        TextKeyEnum textKeyEnum = EnumUtils.getEnum(TextKeyEnum.class, textPush.getContent().toUpperCase());

        if(textKeyEnum == null){

            textKeyEnum = TextKeyEnum.DKF;

        }

        Handler handler = ServiceStaticAccess.getBean(textKeyEnum.name(), Handler.class);

        if (handler == null) {

            // 如果找不到handler，默认去多客服


            throw new WeixinException(ResponseCodes.Weixin.NO_SUCH_SERVICE);

        }

        return handler;
    }

}
