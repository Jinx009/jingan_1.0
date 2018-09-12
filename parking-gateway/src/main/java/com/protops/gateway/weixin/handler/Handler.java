package com.protops.gateway.weixin.handler;

import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.weixin.vo.push.Push;
import com.protops.gateway.weixin.vo.reply.Reply;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhouzhihao on 2015/4/7.
 */
public abstract class Handler {

    private String messageTemplate;

    private String eventKey;

    public Handler(String eventKey){
        this.eventKey = eventKey;
//        this.messageTemplate = WeixinMessage.getTemplate(eventKey);
    }

//    @Autowired
//    public MemberService memberService;
//
//    @Autowired
//    public ParkingLotService parkingLotService;
//
//    @Autowired
//    public VihicleService vihicleService;
//
//    @Autowired
//    public CouponService couponService;
//
//    @Autowired
//    public StatsLogService statsLogService;
//
//    @Autowired
//    public PointUsageRuleService pointUsageRuleService;
//
//    @Autowired
//    public VihiclePayHistoryService vihiclePayHistoryService;
//
//    @Autowired
//    public VihicleClient vihicleClient;

    public abstract Reply handleEvent(Push push) throws WeixinException;


    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }
}
