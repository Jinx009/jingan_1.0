package com.protops.gateway.service;

import com.protops.gateway.dao.ParkingFlowDao;
import com.protops.gateway.dao.ParkingFlowVoDao;
import com.protops.gateway.domain.ParkingFlow;
import com.protops.gateway.vo.order.ParkingFlowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by damen on 2016/6/14.
 */
@Transactional
@Service
public class ParkingFLowService {

    @Autowired
    ParkingFlowDao parkingFlowDao;

    @Autowired
    ParkingFlowVoDao parkingFlowVoDao;

    public void save(ParkingFlow parkingFlow){
        parkingFlowDao.save(parkingFlow);
    }

    public List<ParkingFlowVo> getRecentByMemberId(Integer id){
        return parkingFlowVoDao.getByMemberId(id);
    }
}
