package com.protops.gateway.dao;

import com.protops.gateway.domain.NoticeToExpire;
import com.protops.gateway.domain.ParkingLot;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fanlin on 16/6/18.
 */

@Repository
public class NoticeToExpireDao extends HibernateBaseDao<NoticeToExpire, Integer> {

    public List<NoticeToExpire> getListToSend() {

        String hql = "from NoticeToExpire where recSt = 1 and sent = 0";

        return find(hql);
    }
}
