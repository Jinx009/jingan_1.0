package com.protops.gateway.weixin.vo.reply.detail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author zzh
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ThumbMediaDetail extends MediaDetail {
    @XmlElement(name = "ThumbMediaId")
    private String thumbMediaId;

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

}
