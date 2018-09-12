package com.protops.gateway.dao;

import com.protops.gateway.domain.NoticeExpired;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fanlin on 16/6/18.
 */

@Repository
public class NoticeExpiredDao extends HibernateBaseDao<NoticeExpired, Integer> {

    public List<NoticeExpired> getListToSend() {

        String hql = "from NoticeExpired where recSt = 1 and sent = 0";

        return find(hql);
    }
}
