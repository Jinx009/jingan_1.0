package com.protops.gateway.dao;

import com.protops.gateway.domain.Member;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by zhouzhihao on 2015/4/1.
 */
@Repository
public class MemberDao extends HibernateBaseDao<Member, Integer> {


    public Member getByWeixinId(String weixinId) {

        String hql = "from Member where weixinId = ? and recSt = 1";

        return findUnique(hql, weixinId);
    }

    public Member getByMobile(String mobile) {

        String hql = "from Member where mobile = ? and recSt = 1";

        return findUnique(hql, mobile);
    }
}
