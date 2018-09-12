package com.protops.gateway.dao;

import com.protops.gateway.domain.NoticeExpiring;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fanlin on 16/6/18.
 */
@Repository
public class NoticeExpiringDao extends HibernateBaseDao<NoticeExpiring, Integer> {

    public List<NoticeExpiring> getListToSend() {

        String hql = "from NoticeExpiring where recSt = 1 and sent = 0";

        return find(hql);
    }
}
