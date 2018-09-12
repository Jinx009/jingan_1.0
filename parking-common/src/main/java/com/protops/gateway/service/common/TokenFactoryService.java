package com.protops.gateway.service.common;

import com.protops.gateway.dao.AreaDao;
import com.protops.gateway.dao.LocationDao;
import com.protops.gateway.dao.common.TokenFactoryDao;
import com.protops.gateway.domain.common.TokenFactory;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.service.AppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by jinx on 3/6/17.
 * access_token
 */
@Service
@Transactional
public class TokenFactoryService {

    @Autowired
    private TokenFactoryDao tokenFactoryDao;
    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private AreaDao areaDao;

    /**
     * 新增token
     * @param baseId
     * @return
     */
    public TokenFactory createNew(String baseId,Integer type){
        TokenFactory tokenFactory = new TokenFactory();
        tokenFactory.setBaseId(baseId);
        tokenFactory.setSecret(UUID.randomUUID().toString());
        tokenFactory.setCreateTime(new Date());
        tokenFactory.setType(type);
        tokenFactoryDao.save(tokenFactory);
        return tokenFactory;
    }

    public TokenFactory findByToken(String token){
        return tokenFactoryDao.findByToken(token);
    }

    /**
     * 通过baseId获取token
     * @param baseId
     * @return
     */
    public TokenFactory findByBaseId(String baseId){
        return tokenFactoryDao.findByBaseId(baseId);
    }

    /**
     * 更新token
     * @param tokenFactory
     */
    public void update(TokenFactory tokenFactory){
        tokenFactoryDao.update(tokenFactory);
    }

    /**
     * 新建token
     * @param tokenFactory
     */
    public void save(TokenFactory tokenFactory){
        tokenFactoryDao.save(tokenFactory);
    }


    /**
     * 校验token
     * @param token
     * @return
     */
    public boolean checkToken(String token){
        TokenFactory tokenFactory = tokenFactoryDao.findByToken(token);
        if(tokenFactory!=null){
            long timestamp_now = new Date().getTime();
            if(timestamp_now<tokenFactory.getTimestamp())
                return true;
        }
        return false;
    }

    /**
     * token权限判断
     * @param token
     * @param id
     * @param type
     * @return
     */
    public boolean checkToken(String token,Integer id,Integer type){
        TokenFactory tokenFactory = tokenFactoryDao.findByToken(token);
        if(tokenFactory!=null){
            long timestamp_now = new Date().getTime();
            if(timestamp_now<tokenFactory.getTimestamp()){
                Location location = null;
                if(type == 0){
                    location = locationDao.get(id);
                }else if(type == 1){
                    Area area = areaDao.get(id);
                    location = locationDao.get(area.getLocationId());
                }
                if(location == null||!location.getAppInfoId().equals(tokenFactory.getBaseId())){
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 通过token反查
     * @param token
     * @return
     */
    public TokenFactory getToken(String token){
        return  tokenFactoryDao.findByToken(token);
    }

    /**
     * 失效时间等于当前时间加2小时
     * @return
     */
    public Date getTokenDate(){
        Date date=new Date();
        Calendar calendar   =   new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.HOUR,2);
        date = calendar.getTime();
        return date;
    }

    public Map<String,Object> findBaseToken(String baseId){
        return  tokenFactoryDao.findBaseToken(baseId);
    }

}
