package com.protops.gateway.dao;

import com.protops.gateway.constants.enums.NoticeFlagEnum;
import com.protops.gateway.domain.ParkingLot;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhouzhihao on 2015/4/1.
 */
@Repository
public class ParkingLotDao extends HibernateBaseDao<ParkingLot, Integer> {


    public ParkingLot getByLotNo(String lotNo) {
        String hql = "from ParkingLot where lotNo = ?";
        return findUnique(hql, lotNo);
    }

    public void doUpdate(String lotNo, Integer paid) {
        String hql = "update ParkingLot set paid = ? where lotNo = ?";
        update(hql, lotNo, paid);
    }

    public List<ParkingLot> findTiming(Integer noticeFlag, Integer timing, Integer groupNo, Integer groupCount) {

        Integer startId = (groupNo - 1) * groupCount;
        Integer endId = (groupNo) * groupCount;

        String sql = "select s.id as id, s.lot_no as lotNo, s.current_member_id as currentMemberId, s.create_time as createTime" +
                ", s.connected as connected, s.available as available, s.paid as paid, s.area_id as areaId" +
                ", s.in_time as inTime, s.expire_time as expireTime, s.notice_flag as noticeFlag, s.rec_st as recSt " +
                " from tbl_sensor s " +
                " where id between " + startId + " and " + endId +
                " and rec_st = 1 and available = 1 " +
                " and expire_time < date_add(now(), interval ? minute)" +
                " and notice_flag < ? ";

        return findBySql(sql, ParkingLot.class, timing, noticeFlag);
    }

    public List<ParkingLot> findTimeBeExpired(Integer groupNo, Integer groupCount) {

        return findTiming(NoticeFlagEnum.NOTICE_EXPIRED.getId(), -16, groupNo, groupCount);
    }

    public List<ParkingLot> findTimeToExpire(Integer groupNo, Integer groupCount) {

        return findTiming(NoticeFlagEnum.NOTICE_TO_EXPIRE.getId(), 15, groupNo, groupCount);
    }

    public List<ParkingLot> findTimeBeExpiring(Integer groupNo, Integer groupCount) {

        return findTiming(NoticeFlagEnum.NOTICE_BE_EXPIRING.getId(), 0, groupNo, groupCount);
    }

    public ParkingLot getParkingLotByCurMemberId(Integer id) {
        String hql = "from ParkingLot where currentMemberId = ? and connected = 1 and available = 1 and recSt = 1 order by updateTime desc";
        List<ParkingLot> parkingLots = findLatest(hql, 1, id);
        if (parkingLots == null || parkingLots.size() == 0) {
            return null;
        }
        return parkingLots.get(0);
    }
}
