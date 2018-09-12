package com.protops.gateway.weixin.factory.builder;

import com.protops.gateway.weixin.factory.ReplyProcessorFactory;
import com.protops.gateway.weixin.vo.ReplyDetail;
import com.protops.gateway.weixin.vo.reply.NewsReply;
import com.protops.gateway.weixin.vo.reply.Reply;
import com.protops.gateway.weixin.vo.reply.detail.NewsDetail;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zzh
 */
public class NewsReplyBuilder implements ReplyBuilder {
    @Override
    public Reply buildReply(List<ReplyDetail> replyDetails) {
        int size = replyDetails.size();
        List<NewsDetail> articles = new ArrayList<NewsDetail>(size);

        NewsReply newsReply = new NewsReply();
        newsReply.setMsgType(ReplyProcessorFactory.NEWS.getReplyType());
        newsReply.setArticleCount(size);
        newsReply.setArticles(articles);
        for (ReplyDetail replyDetailVo : replyDetails) {
            articles.add(new NewsDetail(replyDetailVo.getTitle(), replyDetailVo.getDescription(), replyDetailVo.getMediaUrl(), replyDetailVo.getUrl()));
        }

        return newsReply;
    }

}
