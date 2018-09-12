package com.protops.gateway.service.device;

import com.protops.gateway.dao.log.SensorUploadOperationLogDao;
import com.protops.gateway.domain.log.SensorUploadOperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jinx on 4/19/17.
 */
@Service
public class SensorUploadOperationLogService {

    @Autowired
    private SensorUploadOperationLogDao sensorUploadOperationLogDao;

    public void save (SensorUploadOperationLog sensorUploadOperationLog){
        sensorUploadOperationLogDao.save(sensorUploadOperationLog);
    }

}
