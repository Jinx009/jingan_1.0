package com.protops.gateway.service;

import com.protops.gateway.dao.ConfigDao;
import com.protops.gateway.domain.Config;
import com.protops.gateway.vo.ConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhouzhihao on 2014/10/15.
 */

@Service
@Transactional
public class ConfigService {

    @Autowired
    ConfigDao configDao;


    public List<Config> refresh(Integer systemId) {

        return configDao.refresh(systemId);

    }


    public void save(Config config) {
        configDao.save(config);
    }

    public List<Config> update(String key, String value, Integer systemId) {

        configDao.updateConfig(key, value, systemId);

        return refresh(systemId);

    }

    public void save(ConfigVo configVo) {

        Config config = configDao.get(configVo.getId());

        if(config != null){

            config.setValue(configVo.getValue());
        }

        configDao.save(config);
    }


    public List<Config> getKey(String key) {

        return configDao.getKey(key);
    }
}
