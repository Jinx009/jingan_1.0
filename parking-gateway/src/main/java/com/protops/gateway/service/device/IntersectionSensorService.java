package com.protops.gateway.service.device;

import com.protops.gateway.dao.device.IntersectionSensorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by jinx on 3/28/17.
 */
@Service
public class IntersectionSensorService {

    @Autowired
    private IntersectionSensorDao intersectionSensorDao;


}
