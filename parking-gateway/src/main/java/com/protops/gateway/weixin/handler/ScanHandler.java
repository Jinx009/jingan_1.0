package com.protops.gateway.weixin.handler;

import com.protops.gateway.constants.enums.EventKeyEnum;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.util.StringUtils;
import com.protops.gateway.weixin.util.ReplyUtil;
import com.protops.gateway.weixin.vo.push.EventPush;
import com.protops.gateway.weixin.vo.push.Push;
import com.protops.gateway.weixin.vo.reply.Reply;

/**
 * Created by damen on 2015/12/21.
 */
public class ScanHandler extends Handler {

    public ScanHandler() {
        super(EventKeyEnum.scan.name());
    }

    @Override
    public Reply handleEvent(Push push) throws WeixinException {

        EventPush eventPush = (EventPush) push;

        String eventKey = eventPush.getEventKey();

        String message = StringUtils.EMPTY;
        if ("1".equalsIgnoreCase(eventKey)) {

            message = "测试码1：您已获得今天晚上火锅9折券一张，请在会员卡界面查询";

        } else if ("2".equalsIgnoreCase(eventKey)) {

            message = "测试码2： 您已成功抢到汉广元旦PARTY入场号为123002门票一张";

        }

        return ReplyUtil.buildTextReply(message, push);
    }
}
