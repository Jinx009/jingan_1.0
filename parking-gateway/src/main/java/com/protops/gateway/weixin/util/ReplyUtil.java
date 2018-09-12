package com.protops.gateway.weixin.util;

import com.protops.gateway.weixin.factory.ReplyProcessorFactory;
import com.protops.gateway.weixin.vo.ReplyDetail;
import com.protops.gateway.weixin.vo.ReplyDetailWrapper;
import com.protops.gateway.weixin.vo.push.Push;
import com.protops.gateway.weixin.vo.reply.Reply;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.EnumUtils;

import java.util.Arrays;

/**
 *
 * @author zzh
 */
public class ReplyUtil {
    public static Reply buildReply(Reply reply, Push push) {
        try {
            if (reply != null) {
                // keep is new instance
                Reply newReply = (Reply) BeanUtils.cloneBean(reply);

                newReply.setCreateTime(getUnixTimeStamp());
                newReply.setToUserName(push.getFromUserName());
                newReply.setFromUserName(push.getToUserName());

                return newReply;
            }
        } catch (Exception e) {
        }

        return null;
    }

    public static long getUnixTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static Reply parseReplyDetailWarpper(ReplyDetailWrapper replyDetailWarpper) {
        if (replyDetailWarpper == null) {
            return null;
        }

        String replyType = replyDetailWarpper.getReplyType();
        ReplyProcessorFactory replyEnumFactory = EnumUtils.getEnum(ReplyProcessorFactory.class, StringUtils.upperCase(replyType));
        if (replyEnumFactory == null) {
            return null;
        }

        Reply buildReply = replyEnumFactory.buildReply(replyDetailWarpper.getReplyDetails());
        if (buildReply != null) {
            buildReply.setFuncFlag(replyDetailWarpper.getFuncFlag());

            return buildReply;
        }

        return null;
    }

    public static Reply buildTextReply(String message, Push push){

        ReplyDetail replyDetail = new ReplyDetail();

        replyDetail.setDescription(message);

        Reply reply = ReplyUtil.parseReplyDetailWarpper(new ReplyDetailWrapper("text", Arrays.asList(replyDetail)));

        return ReplyUtil.buildReply(reply, push);
    }

    public static Reply buildDkfReply(String message, Push push){

        ReplyDetail replyDetail = new ReplyDetail();

        replyDetail.setDescription(message);

        Reply reply = ReplyUtil.parseReplyDetailWarpper(new ReplyDetailWrapper("transfer_customer_service", Arrays.asList(replyDetail)));

        return ReplyUtil.buildReply(reply, push);
    }

    public static Reply buildNewsReply(ReplyDetail replyDetail, Push push){

        Reply reply = ReplyUtil.parseReplyDetailWarpper(new ReplyDetailWrapper("news", Arrays.asList(replyDetail)));

        return ReplyUtil.buildReply(reply, push);
    }

    /**
     * dummy reply. please according to your own situation to build ReplyDetailWarpper, and remove those code in production.
     */
    public static ReplyDetailWrapper getDummyNewsReplyDetailWarpper() {
        ReplyDetail replyDetail1 = new ReplyDetail();
        replyDetail1.setTitle("fork me");
        replyDetail1.setMediaUrl("http://c.hiphotos.baidu.com/baike/c%3DbaikeA4%2C10%2C95/parseSignStr=c1767bbf4b36acaf4de0c1ad15b2e851/29381f30e924b899a39841be6e061d950b7b02087af4d6b3.jpg");
        replyDetail1.setUrl("https://github.com/usc/wechat-mp-sdk");
        replyDetail1.setDescription("hello world, wechat mp sdk is coming");

        ReplyDetail replyDetail2 = new ReplyDetail();
        replyDetail2.setTitle("star me");
        replyDetail2.setMediaUrl("http://e.hiphotos.baidu.com/baike/pic/item/96dda144ad345982665e49810df431adcaef84c9.jpg");
        replyDetail2.setUrl("https://github.com/usc/wechat-mp-web");
        replyDetail2.setDescription("wechat mp web demo");

        return new ReplyDetailWrapper("news", Arrays.asList(replyDetail1, replyDetail2));
    }
}
