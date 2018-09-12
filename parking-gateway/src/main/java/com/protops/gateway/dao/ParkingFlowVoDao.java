package com.protops.gateway.dao;

import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.vo.order.ParkingFlowVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by damen on 2016/6/15.
 */
@Repository
public class ParkingFlowVoDao extends HibernateBaseDao<ParkingFlowVo, Integer>{

    public List<ParkingFlowVo> getByMemberId(Integer memberId){

        String sql = "select p.id as id, a.name as areaName, p.status as status, p.lot_no as lotNo, date_format(p.parking_time, '%Y-%m') as date, date_format(p.parking_time, '%d') as day, p.parking_time as parkingTime, p.parking_timespan as parkingTimespan  from tbl_parking_flow as p, tbl_area as a where p.area_id = a.id and p.member_id = ?";
        return findBySql(sql, ParkingFlowVo.class, memberId);

    }

}
