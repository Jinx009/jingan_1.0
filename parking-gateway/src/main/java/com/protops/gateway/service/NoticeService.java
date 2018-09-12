package com.protops.gateway.service;

import com.protops.gateway.dao.MemberDao;
import com.protops.gateway.dao.NoticeExpiredDao;
import com.protops.gateway.dao.NoticeExpiringDao;
import com.protops.gateway.dao.NoticeToExpireDao;
import com.protops.gateway.domain.Member;
import com.protops.gateway.domain.NoticeExpired;
import com.protops.gateway.domain.NoticeExpiring;
import com.protops.gateway.domain.NoticeToExpire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fanlin on 16/6/21.
 */
@Service
@Transactional
public class NoticeService {

    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);

    private String noticeTemplateToexpire = "尊敬的用户[%s]您好，您停在[%s]的车辆即将到期，还有15分钟，如您需要继续停车，请及时续费！";
    private String noticeTemplateExpiring = "尊敬的用户[%s]您好，您停在[%s]的车辆已到期，如您需要继续停车，请及时续费，15分钟会将进入超时状态，无法续费！";
    private String noticeTemplateExpired = "尊敬的用户[%s]您好，您停在[%s]的车辆已经超时15分钟，已无法续费，请及时将您的车辆驶离，否则将会被管理员进行贴条处理！";

    @Autowired
    NoticeToExpireDao noticeToExpireDao;

    @Autowired
    NoticeExpiringDao noticeExpiringDao;

    @Autowired
    NoticeExpiredDao noticeExpiredDao;

    @Autowired
    MemberDao memberDao;

    public void noticeToExpire() {

        logger.debug("[Notice to expire] send notice... ");

        List<NoticeToExpire> noticeToExpireList = noticeToExpireDao.getListToSend();

        for(NoticeToExpire noticeToExpire : noticeToExpireList){

            if(noticeToExpire.getUserId() == null)
                continue;

            //send
            Member member = memberDao.get(noticeToExpire.getUserId());
            logger.debug("[Notice to expire] " + String.format(noticeTemplateToexpire, member.getWeixinId(), noticeToExpire.getLotNo()));

            //update
            noticeToExpire.setSent(1);
            noticeToExpireDao.save(noticeToExpire);

        }

        logger.debug("[Notice to expire] send notice... end");

    }

    public void noticeExpiring() {

        logger.debug("[Notice expiring] send notice... ");

        List<NoticeExpiring> noticeExpiringList = noticeExpiringDao.getListToSend();

        for(NoticeExpiring noticeExpiring : noticeExpiringList){

            if(noticeExpiring.getUserId() == null)
                continue;

            //send
            Member member = memberDao.get(noticeExpiring.getUserId());
            logger.debug("[Notice to expire] " + String.format(noticeTemplateExpiring, member.getWeixinId(), noticeExpiring.getLotNo()));

            //update
            noticeExpiring.setSent(1);
            noticeExpiringDao.save(noticeExpiring);


        }
        logger.debug("[Notice expiring] send notice... end");

    }

    public void noticeExpired() {

        logger.debug("[Notice expired] send notice... ");

        List<NoticeExpired> noticeExpiredList = noticeExpiredDao.getListToSend();

        for(NoticeExpired noticeExpired : noticeExpiredList){

            if(noticeExpired.getUserId() == null)
                continue;

            //send
            Member member = memberDao.get(noticeExpired.getUserId());
            logger.debug("[Notice to expire] " + String.format(noticeTemplateExpired, member.getWeixinId(), noticeExpired.getLotNo()));

            //updatea
            noticeExpired.setSent(1);
            noticeExpiredDao.save(noticeExpired);
        }

        logger.debug("[Notice expired] send notice... end");

    }
}
