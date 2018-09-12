package com.protops.gateway.weixin.factory.builder;

import com.protops.gateway.weixin.factory.ReplyProcessorFactory;
import com.protops.gateway.weixin.vo.ReplyDetail;
import com.protops.gateway.weixin.vo.reply.Reply;
import com.protops.gateway.weixin.vo.reply.VoiceReply;
import com.protops.gateway.weixin.vo.reply.detail.MediaDetail;

import java.util.List;

/**
 *
 * @author zzh
 */
public class VoiceReplyBuilder implements ReplyBuilder {
    @Override
    public Reply buildReply(List<ReplyDetail> replyDetails) {
        ReplyDetail detail = replyDetails.get(0);

        MediaDetail voiceDetail = new MediaDetail();
        voiceDetail.setMediaId(detail.getMediaUrl());

        VoiceReply voiceReply = new VoiceReply();
        voiceReply.setMsgType(ReplyProcessorFactory.VOICE.getReplyType());
        voiceReply.setVoiceDetail(voiceDetail);

        return voiceReply;
    }

}
