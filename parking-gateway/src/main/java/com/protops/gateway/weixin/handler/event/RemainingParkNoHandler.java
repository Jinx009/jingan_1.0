//package com.protops.gateway.weixin.handler.event;
//
//import com.protops.gateway.constants.WeixinMessage;
//import com.protops.gateway.constants.enums.EventKeyEnum;
//import com.protops.gateway.constants.enums.StatsAction;
//import com.protops.gateway.domain.ParkingLot;
//import com.protops.gateway.exception.WeixinException;
//import com.protops.gateway.weixin.handler.Handler;
//import com.protops.gateway.weixin.util.ReplyUtil;
//import com.protops.gateway.weixin.vo.push.Push;
//import com.protops.gateway.weixin.vo.reply.Reply;
//import org.apache.commons.lang3.StringUtils;
//
//import java.text.MessageFormat;
//
///**
// * Created by zhouzhihao on 2015/4/7.
// */
//public class RemainingParkNoHandler extends Handler {
//
//
//    public RemainingParkNoHandler(){
//        super(EventKeyEnum.remainingParkNo.name());
//    }
//
//    @Override
//    public Reply handleEvent(Push push) throws WeixinException {
//
//        ParkingLot parkingLot = parkingLotService.getCurrentParkingLot();
//
//        String messageTemplate = WeixinMessage.getTemplate(WeixinMessage.KEY_REMAINING_PARK_NO);
//
//        String newPromotionLink = parkingLot.getNewPromotionLink();
//        if(StringUtils.isEmpty(parkingLot.getNewPromotionLink())){
//            newPromotionLink = StringUtils.EMPTY;
//        }
//
//        String message = MessageFormat.format(messageTemplate, parkingLot.getName(), parkingLot.getRemainingParking(), newPromotionLink);
//
//        statsLogService.log(StatsAction.event, getEventKey(), push.getFromUserName());
//
//        return ReplyUtil.buildTextReply(message, push);
//
//    }
//}
