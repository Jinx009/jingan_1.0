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
public class VoiceReply extends Reply {
    @XmlElement(name = "Voice")
    private MediaDetail voiceDetail;

    public MediaDetail getVoiceDetail() {
        return voiceDetail;
    }

    public void setVoiceDetail(MediaDetail voiceDetail) {
        this.voiceDetail = voiceDetail;
    }

}
