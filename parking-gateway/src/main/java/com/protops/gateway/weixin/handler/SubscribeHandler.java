//package com.protops.gateway.weixin.handler;
//
//import com.protops.gateway.constants.WeixinMessage;
//import com.protops.gateway.constants.enums.EventKeyEnum;
//import com.protops.gateway.domain.ParkingLot;
//import com.protops.gateway.exception.WeixinException;
//import com.protops.gateway.weixin.util.ReplyUtil;
//import com.protops.gateway.weixin.vo.push.Push;
//import com.protops.gateway.weixin.vo.reply.Reply;
//
//import java.text.MessageFormat;
//
///**
// * Created by zhouzhihao on 2015/4/7.
// */
//public class SubscribeHandler extends Handler {
//
//    public SubscribeHandler() {
//        super(EventKeyEnum.subscribe.name());
//    }
//
//    @Override
//    public Reply handleEvent(Push push) throws WeixinException {
//
//        ParkingLot parkingLot = parkingLotService.getCurrentParkingLot();
//
//        String messageTemplate = WeixinMessage.getTemplate(WeixinMessage.KEY_SUBSCRIBE);
//
//        String message = MessageFormat.format(messageTemplate, parkingLot.getName());
//
//        return ReplyUtil.buildTextReply(message, push);
//    }
//}
