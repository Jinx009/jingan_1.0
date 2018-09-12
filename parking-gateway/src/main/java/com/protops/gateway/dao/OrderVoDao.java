package com.protops.gateway.dao;

import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.vo.order.OrderVo;
import com.protops.gateway.vo.order.ParkingFlowVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by damen on 2016/6/15.
 */
@Repository
public class OrderVoDao extends HibernateBaseDao<OrderVo, Integer>{

    public List<OrderVo> getByMemberId(Integer memberId){

        String sql = "select p.id as id, p.order_source as orderType, date_format(p.pay_time, '%H:%i:%S') as payTime, p.status as status,  p.total_amount as amount, date_format(p.pay_time, '%Y-%m') as date, date_format(p.pay_time, '%d') as day from tbl_order as p where p.member_id = ?";
        return findBySql(sql, OrderVo.class, memberId);

    }

}
