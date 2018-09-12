package com.protops.gateway.weixin.vo.reply;

import com.protops.gateway.weixin.vo.reply.detail.ThumbMediaDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author zzh
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class VideoReply extends Reply {
    @XmlElement(name = "Video")
    private ThumbMediaDetail thumbMediaDetail;

    public ThumbMediaDetail getThumbMediaDetail() {
        return thumbMediaDetail;
    }

    public void setThumbMediaDetail(ThumbMediaDetail thumbMediaDetail) {
        this.thumbMediaDetail = thumbMediaDetail;
    }

}
