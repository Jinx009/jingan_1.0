package com.protops.gateway.service.device;

import com.protops.gateway.dao.device.LocationMacVoDao;
import com.protops.gateway.domain.device.LocationMacVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jinx on 4/19/17.
 */
@Service
public class LocationMacVoService {

    @Autowired
    private LocationMacVoDao locationMacVoDao;

    public List<LocationMacVo> findByAppId(Integer appInfoId){
        return locationMacVoDao.getByAppId(appInfoId);
    }

}
