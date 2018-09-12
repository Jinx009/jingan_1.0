//package com.protops.gateway.weixin.handler.event;
//
//import com.protops.gateway.constants.ResponseCodes;
//import com.protops.gateway.constants.WeixinMessage;
//import com.protops.gateway.constants.enums.EventKeyEnum;
//import com.protops.gateway.constants.enums.PayTypeEnum;
//import com.protops.gateway.constants.enums.PlateNumberUsingFlag;
//import com.protops.gateway.constants.enums.StatsAction;
//import com.protops.gateway.domain.Member;
//import com.protops.gateway.domain.PointUsageRule;
//import com.protops.gateway.domain.VihicleInOutFlow;
//import com.protops.gateway.domain.VihiclePayHistory;
//import com.protops.gateway.domain.weixin.payment.VihiclePointsInfo;
//import com.protops.gateway.exception.WeixinException;
//import com.protops.gateway.util.DateUtil;
//import com.protops.gateway.vo.internal.PayHistoryTimespanFactor;
//import com.protops.gateway.weixin.handler.Handler;
//import com.protops.gateway.weixin.util.ReplyUtil;
//import com.protops.gateway.weixin.vo.push.Push;
//import com.protops.gateway.weixin.vo.reply.Reply;
//
//import java.math.BigDecimal;
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by zhouzhihao on 2015/4/7.
// */
//public class PointsUseHandler extends Handler {
//
//    public PointsUseHandler() {
//        super(EventKeyEnum.pointsUse.name());
//    }
//
//    @Override
//    public Reply handleEvent(Push push) throws WeixinException {
//
//        Member member = memberService.getByMemberNumber(push.getFromUserName());
//
//        VihicleInOutFlow vihicleInOutFlow = vihicleService.getLatestVihicleInLog(member, PlateNumberUsingFlag.useAll);
//
//        if (vihicleInOutFlow == null) {
//            throw new WeixinException(ResponseCodes.Vihicle.IN_FLOW_NOT_FOUND);
//        }
//
//        if(vihicleInOutFlow.isAlreadyOut()){
//            throw new WeixinException(ResponseCodes.Weixin.THIS_VIHICLE_HAS_OUT);
//        }
//
//        VihiclePointsInfo vihiclePointsInfo = handlePointsUse(member, vihicleInOutFlow);
//
//        String message = handleQbdk(member, vihiclePointsInfo, vihicleInOutFlow);
//
//        statsLogService.log(StatsAction.event, getEventKey(), push.getFromUserName(), member.getId());
//
//        return ReplyUtil.buildTextReply(message, push);
//
//    }
//
//    public VihiclePointsInfo handlePointsUse(Member member, VihicleInOutFlow vihicleInOutFlow) throws WeixinException {
//
//        String flowNo = vihicleInOutFlow.getFlowNo();
//
//        /*
//         从支付历史中找使用的记录，如果找到，就获取抵扣的时间
//         如果当前使用时间减去所有的使用时间还是大于0，就允许其使用
//          */
//        List<VihiclePayHistory> vihiclePayHistoryList = vihiclePayHistoryService.getByFlowNo(flowNo);
//
//        PayHistoryTimespanFactor payHistoryTimespanFactor = PayHistoryTimespanFactor.init(vihicleInOutFlow, vihiclePayHistoryList);
//
//        // 如果已经抵扣过，就不能再抵扣了
//        if (payHistoryTimespanFactor.isQbdkFinished()) {
//            throw new WeixinException(ResponseCodes.Weixin.NO_MORE_DIKOU);
//        }
//
//        if (payHistoryTimespanFactor.isPaid()) {
//            throw new WeixinException(ResponseCodes.Weixin.NO_MORE_CASH);
//        }
//
//        // 如果还在免停时长里面，就不需要抵扣
//        if (payHistoryTimespanFactor.noMoreDikou() || payHistoryTimespanFactor.lessThan15Min()) {
//            throw new WeixinException(ResponseCodes.Weixin.STILL_FREE);
//        }
//
//        // 超过24小时不能付款
//        if (payHistoryTimespanFactor.exceed24Hours()) {
//            throw new WeixinException(ResponseCodes.Weixin.EXCEED_24_HOURS);
//        }
//
//        // 根据memberType获取积分
//        List<PointUsageRule> pointUsageRuleList = getMemberPointsRules(member);
//
//        // 进行抵扣
//        Integer currentMemberPoints = member.getCurrentPoints();
//
//        if (currentMemberPoints == 0) {
//            throw new WeixinException(ResponseCodes.Weixin.NO_MORE_POINTS);
//        }
//
//        Integer totalPointsRequired = 0;
//        Integer totalFreeTime = 0;
//
//        //我停了133分钟，如果按照小时计算，就是180分钟
//        //如果按照30分钟计算，就是133/30的ceil，然后是5，最后乘以30 = 150分钟
//        //如果按照15分钟计算，就是9×15 = 135分钟
//        Integer actualDikouTime = payHistoryTimespanFactor.getActualDikouTime();
//
//        for (int i = 0; i < pointUsageRuleList.size(); ) {
//
//            PointUsageRule rule = pointUsageRuleList.get(i);
//
//            // 如果全部扣完，就不再抵扣
//            if (actualDikouTime <= 0) break;
//
//            // 如果需要抵扣的分钟数比当前免费的分钟数要多，并且积分够用，则抵扣
//            if (actualDikouTime >= rule.getFreeTime() && currentMemberPoints >= rule.getPointsRequired()) {
//
//                actualDikouTime -= rule.getFreeTime();
//
//                currentMemberPoints -= rule.getPointsRequired();
//
//                totalPointsRequired += rule.getPointsRequired();
//                totalFreeTime += rule.getFreeTime();
//
//            } else {
//                // 只有当前规则不满足了，才换下一个
//                i++;
//            }
//
//        }
//
//        if (currentMemberPoints < totalPointsRequired || totalPointsRequired == 0) {
//            throw new WeixinException(ResponseCodes.Weixin.NO_MORE_POINTS);
//        }
//
//        // 走到这里说明积分抵扣成功。抵扣的时间应该是总体的
//
//        VihiclePointsInfo vihiclePointsInfo = new VihiclePointsInfo(payHistoryTimespanFactor, vihicleInOutFlow);
//        vihiclePointsInfo.setPointsRequired(totalPointsRequired);
//        vihiclePointsInfo.setTotalFreeTime(totalFreeTime);
//
//        return vihiclePointsInfo;
//    }
//
//
//    private List<PointUsageRule> getMemberPointsRules(Member member) {
//
//        String rules = member.getMemberType().getPointsUsageRules();
//
//        List<Integer> rulesIdList = toIntegerList(rules);
//
//        return pointUsageRuleService.getUsageRules(rulesIdList);
//
//    }
//
//    private List<Integer> toIntegerList(String rules) {
//        List<Integer> list = new ArrayList<Integer>();
//        String[] rulesIdArray = rules.split(",");
//        for (String id : rulesIdArray) {
//            list.add(Integer.parseInt(id));
//        }
//        return list;
//    }
//
//    public String handleQbdk(Member member, VihiclePointsInfo vihiclePointsInfo, VihicleInOutFlow vihicleInOutFlow) throws WeixinException {
//
//        String message = org.apache.commons.lang.StringUtils.EMPTY;
//
//        Integer totalPointsRequired = vihiclePointsInfo.getPointsRequired();
//
//        Integer totalFreeTime = vihiclePointsInfo.getTotalFreeTime();
//
//        Integer currentMemberPoints = member.getCurrentPoints();
//
//        // 这里的入场时间就应该是这个入场时间
//        String inTime = DateUtil.getDate(vihicleInOutFlow.getInTime(), DateUtil.DATE_FMT_YMDHMS);
//
//        String payTime = DateUtil.getDate(new Date(), DateUtil.DATE_FMT_YMDHMS);
//
//        // 先通知停车场
//        boolean ok = vihicleClient.noticeParkingLot(vihicleInOutFlow.getOutFlowNo(), vihicleInOutFlow.getFullPlateNumber(), String.valueOf(totalFreeTime), payTime, inTime);
//
//        VihiclePayHistory vihiclePayHistory = new VihiclePayHistory();
//        vihiclePayHistory.setPlateNumber(vihicleInOutFlow.getPlateNumber());
//        vihiclePayHistory.setFlowNo(vihicleInOutFlow.getFlowNo());
//        vihiclePayHistory.setParkTimespan(totalFreeTime);
//        vihiclePayHistory.setPoints(totalPointsRequired);
//        vihiclePayHistory.setPayTime(new Date());
//        vihiclePayHistory.setPayType(PayTypeEnum.points.getPayType());
//
//        if(!ok){
//
//            vihiclePayHistory.setStatus(VihiclePayHistory.STATUS_FAIL);
//
//            memberService.doPointsDikou(member, vihiclePayHistory, totalPointsRequired, currentMemberPoints);
//
//            // 如果失败，直接异常出去。并且记录异常表
//            throw new WeixinException(ResponseCodes.Weixin.QBDK_SEND_TO_PARKING_LOT_ERR);
//
//        }
//
//        memberService.doPointsDikou(member, vihiclePayHistory, totalPointsRequired, currentMemberPoints);
//
//        BigDecimal result = new BigDecimal(totalFreeTime).divide(new BigDecimal(60), BigDecimal.ROUND_CEILING);
//
//        PayHistoryTimespanFactor payHistoryTimespanFactor = vihiclePointsInfo.getFactor();
//
//
//        if (payHistoryTimespanFactor.getFreeTimespan() > 0) {
//
//            String qbdkFreeMessage = WeixinMessage.getTemplate(WeixinMessage.KEY_QBDK_FREE_POINTS);
//
//            message = MessageFormat.format(qbdkFreeMessage, currentMemberPoints, payHistoryTimespanFactor.getFreeTimespan(), Integer.parseInt(result.toString()), vihicleInOutFlow.getOutFlowNo());
//
//        } else {
//
//            String messageTemplate = WeixinMessage.getTemplate(WeixinMessage.KEY_QBDK_POINTS);
//
//            message = MessageFormat.format(messageTemplate, currentMemberPoints, Integer.parseInt(result.toString()), vihicleInOutFlow.getOutFlowNo());
//
//        }
//
//        return message;
//    }
//}
