package com.protops.gateway.weixin.factory.builder;

import com.protops.gateway.weixin.factory.ReplyProcessorFactory;
import com.protops.gateway.weixin.vo.ReplyDetail;
import com.protops.gateway.weixin.vo.reply.ImageReply;
import com.protops.gateway.weixin.vo.reply.Reply;
import com.protops.gateway.weixin.vo.reply.detail.MediaDetail;

import java.util.List;


/**
 *
 * @author zzh
 */
public class ImageReplyBuilder implements ReplyBuilder {
    @Override
    public Reply buildReply(List<ReplyDetail> replyDetails) {
        ReplyDetail detail = replyDetails.get(0);

        MediaDetail imageDetail = new MediaDetail();
        imageDetail.setMediaId(detail.getMediaUrl());

        ImageReply imageReply = new ImageReply();
        imageReply.setMsgType(ReplyProcessorFactory.IMAGE.getReplyType());
        imageReply.setImageDetail(imageDetail);

        return imageReply;
    }

}
