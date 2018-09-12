package com.protops.gateway.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by gaoxiaoling on 9/23/14.
 */
public class RandomIdentity {

    private static final Logger logger = LoggerFactory.getLogger(RandomIdentity.class);
    /**
     * 几个用于生成随机串的参数
     */
    protected final static String SESSION_ID_RANDOM_ALGORITHM = "SHA1PRNG";
    protected final static String SESSION_ID_RANDOM_ALGORITHM_ALT = "IBMSecureRandom";
    protected Random _random;
    private boolean _weakRandom;

    public RandomIdentity(){
        try{
            _random= SecureRandom.getInstance(SESSION_ID_RANDOM_ALGORITHM);
            logger.info("using {} random algorithm", SESSION_ID_RANDOM_ALGORITHM);
        }catch (NoSuchAlgorithmException e){
            try{
                _random=SecureRandom.getInstance(SESSION_ID_RANDOM_ALGORITHM_ALT);
                logger.info("using {} random algorithm", SESSION_ID_RANDOM_ALGORITHM_ALT);
            }catch (NoSuchAlgorithmException e_alt){
                _random=new Random();
                logger.info("using java.util.Random() as random algorithm");
            }
        }

        _random.setSeed(_random.nextLong()^System.currentTimeMillis()^hashCode()^Runtime.getRuntime().freeMemory());
    }

    public String next(){
        String id=null;
        while (id==null||id.length()==0 ){
            long r = _random.nextLong();

            if (r<0){
                r=-r;
            }

            id=Long.toString(r,36);
        }

        return id;
    }
}
