package com.protops.gateway.service.weixin;

import com.protops.gateway.dao.ChargePolicyDao;
import com.protops.gateway.domain.ChargePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by damen on 2016/6/12.
 */
@Service
@Transactional
public class ChargePolicyService {

    @Autowired
    ChargePolicyDao chargePolicyDao;


    public ChargePolicy get(Integer chargePolicyId) {

        return chargePolicyDao.get(chargePolicyId);
    }
}
