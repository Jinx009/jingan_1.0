package com.protops.gateway.weixin.factory.builder;

import com.protops.gateway.weixin.factory.ReplyProcessorFactory;
import com.protops.gateway.weixin.vo.ReplyDetail;
import com.protops.gateway.weixin.vo.reply.MusicReply;
import com.protops.gateway.weixin.vo.reply.Reply;
import com.protops.gateway.weixin.vo.reply.detail.MusicDetail;

import java.util.List;

/**
 *
 * @author zzh
 */
public class MusicReplyBuilder implements ReplyBuilder {
    @Override
    public Reply buildReply(List<ReplyDetail> replyDetails) {
        ReplyDetail detail = replyDetails.get(0);

        MusicDetail musicDetail = new MusicDetail(detail.getTitle(), detail.getDescription(), detail.getMediaUrl(), detail.getUrl());

        MusicReply musicReply = new MusicReply();
        musicReply.setMsgType(ReplyProcessorFactory.MUSIC.getReplyType());
        musicReply.setMusicDetail(musicDetail);

        return musicReply;
    }

}
