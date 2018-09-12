package com.protops.gateway.dao.common;

import com.protops.gateway.domain.common.TokenFactory;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinx on 3/6/17.
 * access_token
 */
@Repository
@Transactional
public class TokenFactoryDao extends HibernateBaseDao<TokenFactory,Integer>{

    public TokenFactory findByToken(String token){
        String hql = " FROM TokenFactory WHERE token = ?  ";
        return findUnique(hql,token);
    }

    public TokenFactory findByBaseId(String baseId){
        String hql = " FROM TokenFactory WHERE baseId = ? ";
        return findUnique(hql,baseId);
    }

    public void update(TokenFactory tokenFactory){
        super.save(tokenFactory);
    }

    public Map<String,Object> findBaseToken(String baseId){
        String hql = "  FROM TokenFactory a WHERE a.baseId = ? ";
        TokenFactory tokenFactory = findUnique(hql,baseId);
        if(tokenFactory!=null){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("token",tokenFactory.getToken());
            map.put("timestamp",tokenFactory.getTimestamp());
            return map;
        }
        return null;
    }

}
