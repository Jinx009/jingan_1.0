package com.protops.gateway.dao;

import com.protops.gateway.domain.Order;
import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.util.Page;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by zhouzhihao on 2015/3/9.
 */

@Repository
public class OrderDao extends HibernateBaseDao<Order, Integer> {

    public void updateOrder(String orderNumber, Integer orderStatus) {
        String hql = "update Order set status = ? where orderNumber = ?";
        update(hql, orderStatus, orderNumber);
    }

    public void updateOrder(String orderNumber, String transactionId, Date payTime, Integer orderStatus) {
        String hql = "update Order set transId = ?, payTime = ?, status = ? where orderNumber = ?";
        update(hql, transactionId, payTime, orderStatus, orderNumber);
    }

    public Order getByOrderNumber(String orderNumber) {
        String hql = "from Order where orderNumber = ? and recSt = 1";
        return findUnique(hql, orderNumber);

    }

    public List<Order> pagedList(Page<Order> page) {
        String hql = "from Order where recSt = 1 order by id desc";
        return find(page, hql).getResult();
    }

    public int getTotalCount() {
        String hql = "select count(*) from Order where recSt = 1";
        return findLong(hql).intValue();
    }

    public int getTotalCountByStatus(int status) {
        String hql = "select count(*) from Order where status = ? and recSt = 1";
        return findLong(hql, status).intValue();
    }

    public List<Order> pageListByStatus(Page<Order> page, Integer status) {
        String hql = "from Order where status = ? and recSt = 1 order by id desc";
        return find(page, hql, status).getResult();
    }

    public List<Order> getRecentOrders(Integer id) {
        String hql = "from Order where memberId = ? and recSt = 1 order by id desc";
        return findLatest(hql, 5, id);
    }

    public Order getRecentOrder(Integer id, Date date) {
        String hql = "from Order where memberId = ? and status = 2 and recSt = 1 and expireTime > ? order by id desc";
        List<Order> orders = findLatest(hql, 1, id, date);
        if(orders == null || orders.size() == 0){
            return null;
        }
        return orders.get(0);
    }
}
