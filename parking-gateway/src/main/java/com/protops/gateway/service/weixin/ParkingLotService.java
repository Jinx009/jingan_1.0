package com.protops.gateway.service.weixin;

import com.protops.gateway.dao.ParkingLotDao;
import com.protops.gateway.domain.ParkingLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by damen on 2016/6/13.
 */
@Service
@Transactional
public class ParkingLotService {

    @Autowired
    ParkingLotDao parkingLotDao;

    public ParkingLot getByLotNo(String lotNo) {

        return parkingLotDao.getByLotNo(lotNo);


    }

    public void updatePaid(String lotNo) {
        parkingLotDao.doUpdate(lotNo, 1);
    }

    public void save(ParkingLot parkingLot) {
        parkingLotDao.save(parkingLot);
    }

    // 该接口判断必须可以连，并且是有车状态下的
    public ParkingLot getParkingLotByCurMemberId(Integer id) {
        return parkingLotDao.getParkingLotByCurMemberId(id);
    }
}
