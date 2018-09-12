package com.protops.gateway.service.weixin;

import com.protops.gateway.constants.enums.OrderStatus;
import com.protops.gateway.dao.OrderDao;
import com.protops.gateway.dao.OrderVoDao;
import com.protops.gateway.domain.Order;
import com.protops.gateway.vo.order.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zhouzhihao on 2015/7/27.
 */

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderVoDao orderVoDao;

    public List<OrderVo> getByMemberId(Integer memberId){
        return orderVoDao.getByMemberId(memberId);
    }

    public void save(Order order) {

        orderDao.save(order);

    }

    public void save(String prepayId) {
        Order order = new Order();
        order.setOrderTime(new Date());
        order.setPrepayId(prepayId);
        order.setStatus(Order.ORDER_STATUS_UNPAID);
        orderDao.save(order);
    }

    public Order getByOrderNumber(String orderNumber) {

        return orderDao.getByOrderNumber(orderNumber);
    }

    public void notifyOk(Order order, String transId) {
        order.setStatus(OrderStatus.paid.getStatus());
        order.setPayTime(new Date());
        order.setTransId(transId);

        orderDao.save(order);
    }

    public void tagRefund(String orderNumber) {
        orderDao.updateOrder(orderNumber, Order.ORDER_STATUS_REFUNDING);
    }

    public List<Order> queryOrders(Integer id) {
        
        return orderDao.getRecentOrders(id);

    }
}
