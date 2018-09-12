package com.protops.gateway.weixin.handler.text;

import com.protops.gateway.constants.enums.TextKeyEnum;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.weixin.handler.Handler;
import com.protops.gateway.weixin.util.ReplyUtil;
import com.protops.gateway.weixin.vo.push.Push;
import com.protops.gateway.weixin.vo.reply.Reply;

/**
 * Created by zhouzhihao on 2015/8/28.
 */
public class DkfHandler extends Handler {

    public DkfHandler() {
        super(TextKeyEnum.DKF.name());
    }

    @Override
    public Reply handleEvent(Push push) throws WeixinException {

        return ReplyUtil.buildDkfReply("", push);



    }
}
