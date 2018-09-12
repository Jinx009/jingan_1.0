package com.protops.gateway.service.common;

import com.alibaba.fastjson.JSONObject;
import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.constants.common.SignConstant;
import com.protops.gateway.dao.common.TokenFactoryDao;
import com.protops.gateway.domain.common.TokenFactory;
import com.protops.gateway.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jinx on 3/13/17.
 */
@Transactional
@Service
public class SignService {

    @Autowired
    private TokenFactoryDao tokenFactoryDao;

    private static final Logger logger = LoggerFactory.getLogger(SignService.class);

    public boolean checkSign(JSONObject jsonObject){
        String baseId = jsonObject.getString(HttpConstant.BASE_ID);
        String data = jsonObject.getString(HttpConstant.DATA);
        String sign = jsonObject.getString(HttpConstant.SIGN);
        TokenFactory tokenFactory = tokenFactoryDao.findByBaseId(baseId);
        if(tokenFactory==null){
            String signStr = baseId+data+ SignConstant.STATIC_SIGN;
            logger.warn(sign+"--base--"+signStr);
            if(MD5Utils.MD5(signStr).equals(sign))
                return true;
        }else{
            String signStr = baseId+data+tokenFactory.getSecret();
            logger.warn(sign+"--register--"+signStr);
            if(MD5Utils.MD5(signStr).equals(sign))
                return true;
        }
        return false;
    }

}
