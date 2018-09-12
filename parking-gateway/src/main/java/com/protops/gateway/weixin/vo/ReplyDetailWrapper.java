package com.protops.gateway.weixin.vo;

import java.util.List;

/**
 *
 * @author zzh
 */
public class ReplyDetailWrapper {
    private String replyType;
    private int funcFlag = 0;
    private List<ReplyDetail> replyDetails;

    public ReplyDetailWrapper(String replyType, List<ReplyDetail> replyDetails) {
        this(replyType, 0, replyDetails);
    }

    public ReplyDetailWrapper(String replyType, int funcFlag, List<ReplyDetail> replyDetails) {
        this.replyType = replyType;
        this.funcFlag = funcFlag;
        this.replyDetails = replyDetails;
    }

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public int getFuncFlag() {
        return funcFlag;
    }

    public List<ReplyDetail> getReplyDetails() {
        return replyDetails;
    }

    public void setReplyDetails(List<ReplyDetail> replyDetails) {
        this.replyDetails = replyDetails;
    }

}
