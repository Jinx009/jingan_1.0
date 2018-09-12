package com.protops.gateway.service;

import com.protops.gateway.constants.enums.NoticeFlagEnum;
import com.protops.gateway.constants.enums.ParkingLotStatus;
import com.protops.gateway.dao.NoticeExpiredDao;
import com.protops.gateway.dao.NoticeExpiringDao;
import com.protops.gateway.dao.NoticeToExpireDao;
import com.protops.gateway.dao.ParkingLotDao;
import com.protops.gateway.domain.NoticeExpired;
import com.protops.gateway.domain.NoticeExpiring;
import com.protops.gateway.domain.NoticeToExpire;
import com.protops.gateway.domain.ParkingLot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fanlin on 16/6/18.
 */
@Service
@Transactional
public class PollingService {

    private static final Logger logger = LoggerFactory.getLogger(PollingService.class);

    @Autowired
    ParkingLotDao parkingLotDao;

    @Autowired
    NoticeToExpireDao noticeToExpireDao;

    @Autowired
    NoticeExpiringDao noticeExpiringDao;

    @Autowired
    NoticeExpiredDao noticeExpiredDao;


    public void pollingToExpire(Integer groupNo, Integer groupCount) {

        logger.debug("[Polling For toBeExpire] finding parkingLot which is to be expired...");

        List<ParkingLot> parkingLotList = parkingLotDao.findTimeToExpire(groupNo, groupCount);

        for(ParkingLot parkingLot : parkingLotList){

            NoticeToExpire noticeToExpire = new NoticeToExpire();
            noticeToExpire.setLotNo(parkingLot.getLotNo());
            noticeToExpire.setSensorId(parkingLot.getId());
            noticeToExpire.setUserId(parkingLot.getCurrentMemberId());
            noticeToExpire.setExpireTime(parkingLot.getExpireTime());
            noticeToExpire.setSent(0);

            noticeToExpireDao.save(noticeToExpire);

            logger.debug("[Polling For toBeExpire] " + String.format("parkingLot[%s] memberId[%s] expireTime [%s] is to be expire", parkingLot.getLotNo(), parkingLot.getCurrentMemberId(), parkingLot.getExpireTime()));

            parkingLot.setNoticeFlag(NoticeFlagEnum.NOTICE_TO_EXPIRE.getId());
            parkingLotDao.save(parkingLot);

            logger.debug("[Polling For toBeExpire] " + String.format("parkingLot[%s] memberId[%s] expireTime [%s] is update to noticeToExpire", parkingLot.getLotNo(), parkingLot.getCurrentMemberId(), parkingLot.getExpireTime()));
        }

        logger.debug("[Polling For toBeExpire] finding parkingLot which is to be expired...end");
    }

    public void pollingExpiring(Integer groupNo, Integer groupCount) {

        logger.debug("[Polling For beExpiring] finding parkingLot which is expiring...");

        List<ParkingLot> parkingLotList = parkingLotDao.findTimeBeExpiring(groupNo, groupCount);

        for(ParkingLot parkingLot : parkingLotList){

            NoticeExpiring noticeExpiring = new NoticeExpiring();
            noticeExpiring.setLotNo(parkingLot.getLotNo());
            noticeExpiring.setSensorId(parkingLot.getId());
            noticeExpiring.setUserId(parkingLot.getCurrentMemberId());
            noticeExpiring.setExpireTime(parkingLot.getExpireTime());
            noticeExpiring.setSent(0);

            noticeExpiringDao.save(noticeExpiring);

            logger.debug("[Polling For beExpiring] " + String.format("parkingLot[%s] memberId[%s] expireTime [%s] is expiring", parkingLot.getLotNo(), parkingLot.getCurrentMemberId(), parkingLot.getExpireTime()));

            parkingLot.setNoticeFlag(NoticeFlagEnum.NOTICE_BE_EXPIRING.getId());
            parkingLotDao.save(parkingLot);

            logger.debug("[Polling For beExpiring] " + String.format("parkingLot[%s] memberId[%s] expireTime [%s] is update to noticeExpiring", parkingLot.getLotNo(), parkingLot.getCurrentMemberId(), parkingLot.getExpireTime()));
        }

        logger.debug("[Polling For beExpiring] finding parkingLot which is expiring...end");
    }

    public void pollingExpired(Integer groupNo, Integer groupCount) {

        logger.debug("[Polling For Expired] finding parkingLot which is expired...");

        List<ParkingLot> parkingLotList = parkingLotDao.findTimeBeExpired(groupNo, groupCount);

        for(ParkingLot parkingLot : parkingLotList){

            NoticeExpired noticeExpired = new NoticeExpired();
            noticeExpired.setLotNo(parkingLot.getLotNo());
            noticeExpired.setSensorId(parkingLot.getId());
            noticeExpired.setUserId(parkingLot.getCurrentMemberId());
            noticeExpired.setExpireTime(parkingLot.getExpireTime());
            noticeExpired.setSent(0);

            noticeExpiredDao.save(noticeExpired);

            logger.debug("[Polling For Expired] " + String.format("parkingLot[%s] memberId[%s] expireTime [%s] is expired", parkingLot.getLotNo(), parkingLot.getCurrentMemberId(), parkingLot.getExpireTime()));

            parkingLot.setPaid(ParkingLotStatus.expired.getId()); //
            parkingLot.setNoticeFlag(NoticeFlagEnum.NOTICE_EXPIRED.getId());
            parkingLotDao.save(parkingLot);

            logger.debug("[Polling For Expired] " + String.format("parkingLot[%s] memberId[%s] expireTime [%s] is update to noticeExpired", parkingLot.getLotNo(), parkingLot.getCurrentMemberId(), parkingLot.getExpireTime()));
        }

        logger.debug("[Polling For Expired] finding parkingLot which is expired...end");
    }

    public void print(Object threadPoolTaskData) {

        System.out.println("threadPoolTaskData: " + threadPoolTaskData);

        ParkingLotDao parkingLotDao = new ParkingLotDao();

        List<ParkingLot> parkingLotList = parkingLotDao.findTimeBeExpiring(1, 5);

        System.out.println(parkingLotList.size());
    }
}

