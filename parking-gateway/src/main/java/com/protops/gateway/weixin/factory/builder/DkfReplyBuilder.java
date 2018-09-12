package com.protops.gateway.weixin.factory.builder;

import com.protops.gateway.weixin.factory.ReplyProcessorFactory;
import com.protops.gateway.weixin.vo.ReplyDetail;
import com.protops.gateway.weixin.vo.reply.DkfReply;
import com.protops.gateway.weixin.vo.reply.Reply;

import java.util.List;


/**
 *
 * @author zzh
 */
public class DkfReplyBuilder implements ReplyBuilder {

    @Override
    public Reply buildReply(List<ReplyDetail> replyDetails) {

        DkfReply textReply = new DkfReply();

        textReply.setMsgType(ReplyProcessorFactory.TRANSFER_CUSTOMER_SERVICE.getReplyType());

        return textReply;
    }

}
