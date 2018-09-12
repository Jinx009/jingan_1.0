package com.protops.gateway.weixin.vo.reply;

import com.protops.gateway.weixin.vo.reply.detail.MusicDetail;

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
public class MusicReply extends Reply {
    @XmlElement(name = "Music")
    private MusicDetail musicDetail;

    public MusicDetail getMusicDetail() {
        return musicDetail;
    }

    public void setMusicDetail(MusicDetail musicDetail) {
        this.musicDetail = musicDetail;
    }

}
