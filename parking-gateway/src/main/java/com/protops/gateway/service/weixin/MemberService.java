package com.protops.gateway.service.weixin;

import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.dao.MemberDao;
import com.protops.gateway.domain.Member;
import com.protops.gateway.exception.WeixinException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by damen on 2016/6/11.
 */
@Service
@Transactional
public class MemberService {

    @Autowired
    MemberDao memberDao;

    public Member getByWeixinId(String weixinId) throws WeixinException {
        return memberDao.getByWeixinId(weixinId);
    }

    public Member getByMobile(String mobile) {
        return memberDao.getByMobile(mobile);
    }

    public void save(Member member) {
        memberDao.save(member);
    }
}
