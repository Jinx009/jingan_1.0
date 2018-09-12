//package com.protops.gateway.weixin.handler.event;
//
//import com.protops.gateway.constants.ResponseCodes;
//import com.protops.gateway.constants.WeixinMessage;
//import com.protops.gateway.constants.enums.EventKeyEnum;
//import com.protops.gateway.constants.enums.StatsAction;
//import com.protops.gateway.domain.Member;
//import com.protops.gateway.exception.WeixinException;
//import com.protops.gateway.util.DateUtil;
//import com.protops.gateway.utils.MemberUtil;
//import com.protops.gateway.weixin.handler.Handler;
//import com.protops.gateway.weixin.util.ReplyUtil;
//import com.protops.gateway.weixin.vo.push.Push;
//import com.protops.gateway.weixin.vo.reply.Reply;
//
//import java.text.MessageFormat;
//import java.util.Date;
//
///**
// * Created by zhouzhihao on 2015/4/7.
// */
//
//public class PointsQueryHandler extends Handler {
//
//    public PointsQueryHandler() {
//        super(EventKeyEnum.pointsQuery.name());
//    }
//
//    @Override
//    public Reply handleEvent(Push push) throws WeixinException {
//
//        Member member = memberService.getActiveMemberByWeixinId(push.getFromUserName());
//
//        if (member == null) {
//            throw new WeixinException(ResponseCodes.Weixin.YOU_ARE_NOT_ONE_OF_US);
//        }
//
//        String messageTemplate = WeixinMessage.getTemplate(WeixinMessage.KEY_POINTS_QUERY_MSG);
//
//        Date expiredTime = member.getExpiredTime();
//        if (expiredTime == null) {
//            expiredTime = MemberUtil.getInitMemberExpiredTime();
//        }
//        String displayDate = DateUtil.displayChinaYMD(expiredTime);
//
//
//        String message = MessageFormat.format(messageTemplate, member.getCurrentPoints(), displayDate);
//
//        statsLogService.log(StatsAction.event, getEventKey(), push.getFromUserName(), member.getId());
//
//        return ReplyUtil.buildTextReply(message, push);
//
//    }
//}
