package com.protops.gateway.dao.log;

import com.protops.gateway.domain.log.IntersectionSensorDeviceLog;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jinx on 3/13/17.
 */
@Repository
@Transactional
public class IntersectionSensorDeviceLogDao extends HibernateBaseDao<IntersectionSensorDeviceLog,Integer>{
}
