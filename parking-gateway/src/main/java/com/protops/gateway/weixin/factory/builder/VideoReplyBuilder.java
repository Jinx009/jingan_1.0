package com.protops.gateway.weixin.factory.builder;

import com.protops.gateway.weixin.factory.ReplyProcessorFactory;
import com.protops.gateway.weixin.vo.ReplyDetail;
import com.protops.gateway.weixin.vo.reply.Reply;
import com.protops.gateway.weixin.vo.reply.VideoReply;
import com.protops.gateway.weixin.vo.reply.detail.ThumbMediaDetail;

import java.util.List;

/**
 *
 * @author zzh
 */
public class VideoReplyBuilder implements ReplyBuilder {
    @Override
    public Reply buildReply(List<ReplyDetail> replyDetails) {
        ReplyDetail detail = replyDetails.get(0);

        ThumbMediaDetail thumbMediaDetail = new ThumbMediaDetail();
        thumbMediaDetail.setMediaId(detail.getMediaUrl());
        thumbMediaDetail.setThumbMediaId(detail.getUrl());

        VideoReply videoReply = new VideoReply();
        videoReply.setMsgType(ReplyProcessorFactory.VIDEO.getReplyType());
        videoReply.setThumbMediaDetail(thumbMediaDetail);

        return videoReply;
    }

}
