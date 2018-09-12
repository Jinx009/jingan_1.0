package com.protops.gateway.weixin.factory.builder;

import com.protops.gateway.weixin.vo.ReplyDetail;
import com.protops.gateway.weixin.vo.reply.Reply;

import java.util.List;

/**
 *
 * @author zzh
 */
public interface ReplyBuilder {
    Reply buildReply(List<ReplyDetail> replyDetails);
}
