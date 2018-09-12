package com.protops.gateway.weixin.factory;

import com.protops.gateway.weixin.factory.builder.*;
import com.protops.gateway.weixin.vo.ReplyDetail;
import com.protops.gateway.weixin.vo.reply.Reply;

import java.util.List;

/**
 * @author zzh
 */
public enum ReplyProcessorFactory {
    TEXT("text") {
        @Override
        protected ReplyBuilder getReplyBuilder() {
            return new TextReplyBuilder();
        }
    },
    NEWS("news") {
        @Override
        protected ReplyBuilder getReplyBuilder() {
            return new NewsReplyBuilder();
        }
    },
    MUSIC("music") {
        @Override
        protected ReplyBuilder getReplyBuilder() {
            return new MusicReplyBuilder();
        }
    },
    IMAGE("image") {
        @Override
        protected ReplyBuilder getReplyBuilder() {
            return new ImageReplyBuilder();
        }
    },
    VOICE("voice") {
        @Override
        protected ReplyBuilder getReplyBuilder() {
            return new VoiceReplyBuilder();
        }
    },
    VIDEO("video") {
        @Override
        protected ReplyBuilder getReplyBuilder() {
            return new VideoReplyBuilder();
        }
    },
    TRANSFER_CUSTOMER_SERVICE("transfer_customer_service") {
        @Override
        protected ReplyBuilder getReplyBuilder() {
            return new DkfReplyBuilder();
        }
    };

    private String replyType;

    private ReplyProcessorFactory(String replyType) {
        this.replyType = replyType;
    }

    protected abstract ReplyBuilder getReplyBuilder();

    public Reply buildReply(List<ReplyDetail> replyDetails) {

        if (replyDetails == null || replyDetails.isEmpty()) {
            return null;
        }

        return getReplyBuilder().buildReply(replyDetails);
    }

    public String getReplyType() {
        return replyType;
    }

}
