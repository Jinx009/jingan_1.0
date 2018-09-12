package com.protops.gateway.weixin.vo.reply;

import com.protops.gateway.weixin.vo.reply.detail.NewsDetail;

import javax.xml.bind.annotation.*;
import java.util.List;


/**
 *
 * @author zzh
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewsReply extends Reply {
    @XmlElement(name = "ArticleCount")
    private int ArticleCount;

    @XmlElementWrapper(name = "Articles")
    @XmlElement(name = "item")
    private List<NewsDetail> articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<NewsDetail> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsDetail> articles) {
        this.articles = articles;
    }

}
