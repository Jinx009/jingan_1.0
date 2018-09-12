package com.protops.gateway.dao;

import com.protops.gateway.domain.Config;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhouzhihao on 2014/10/15.
 */
@Repository
public class ConfigDao extends HibernateBaseDao<Config, Integer> {

    public List<Config> refresh(Integer systemId) {

        String hql = "from Config where systemId = ? and recSt=1";

        return find(hql, systemId);

    }

    public void updateConfig(String key, String value, Integer systemId) {

        String hql = "update Config set key=?, value=?, systemId=? where key=?";

        update(hql, key, value, systemId, key);

    }

    public List<Config> getKey(String key) {

        String hql = "from Config where key = ? and recSt = ?";

        return find(hql, key, 1);

    }
}
