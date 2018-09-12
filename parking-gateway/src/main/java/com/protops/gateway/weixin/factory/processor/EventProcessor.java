package com.protops.gateway.weixin.factory.processor;

import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.constants.enums.EventKeyEnum;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.utils.ServiceStaticAccess;
import com.protops.gateway.weixin.handler.Handler;
import com.protops.gateway.weixin.vo.push.EventPush;
import com.protops.gateway.weixin.vo.push.Push;
import org.apache.commons.lang3.EnumUtils;

/**
 * @author zzh
 */
public class EventProcessor extends MessageProcessor {

    public EventProcessor(String message, Class<? extends Push> pushClass) {
        super(message, pushClass);
    }

    @Override
    public Handler getEventHandler() throws WeixinException {

        EventPush eventPush = (EventPush) push;

        // 如果是sbuscribe
        Handler handler = returnSubscribeHandler(eventPush);

        if (handler != null) {
            return handler;
        }

        Handler scanHandler = returnScanHandler(eventPush);
        if (scanHandler != null) {
            return scanHandler;
        }


        EventKeyEnum eventKeyEnum = EnumUtils.getEnum(EventKeyEnum.class, eventPush.getEventKey());

        if (eventKeyEnum == null) {
            throw new WeixinException(ResponseCodes.Weixin.NO_SUCH_SERVICE);
        }

        handler = ServiceStaticAccess.getBean(eventKeyEnum.name(), Handler.class);

        if (handler == null) {

            throw new WeixinException(ResponseCodes.Weixin.NO_SUCH_SERVICE);

        }

        return handler;
    }

    private Handler returnScanHandler(EventPush eventPush) {

        String scanEvent = EventKeyEnum.scan.name();

        if (scanEvent.equalsIgnoreCase(eventPush.getEvent())) {

            Handler handler = ServiceStaticAccess.getBean(scanEvent, Handler.class);

            return handler;
        }

        return null;

    }

    private Handler returnSubscribeHandler(EventPush eventPush) {

        String sbuscribeEvent = EventKeyEnum.subscribe.name();

        if (sbuscribeEvent.equals(eventPush.getEvent())) {

            Handler handler = ServiceStaticAccess.getBean(sbuscribeEvent, Handler.class);

            return handler;

        }

        return null;

    }


}
