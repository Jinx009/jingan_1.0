package com.protops.gateway.weixin.vo.reply;

import com.protops.gateway.weixin.vo.reply.detail.MediaDetail;

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
public class ImageReply extends Reply {
    @XmlElement(name = "Image")
    private MediaDetail imageDetail;

    public MediaDetail getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(MediaDetail imageDetail) {
        this.imageDetail = imageDetail;
    }

}
