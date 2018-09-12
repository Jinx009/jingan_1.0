package com.protops.gateway.weixin.factory.builder;

import com.protops.gateway.weixin.factory.ReplyProcessorFactory;
import com.protops.gateway.weixin.vo.ReplyDetail;
import com.protops.gateway.weixin.vo.reply.Reply;
import com.protops.gateway.weixin.vo.reply.TextReply;

import java.util.List;


/**
 *
 * @author zzh
 */
public class TextReplyBuilder implements ReplyBuilder {

    @Override
    public Reply buildReply(List<ReplyDetail> replyDetails) {

        ReplyDetail detail = replyDetails.get(0);

        TextReply textReply = new TextReply();

        textReply.setMsgType(ReplyProcessorFactory.TEXT.getReplyType());

        textReply.setContent(detail.getDescription());

        return textReply;
    }

}
