package com.protops.gateway.dao.log;

import com.protops.gateway.domain.log.RouterDeviceLog;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jinx on 3/13/17.
 * 接收机设备日志
 */
@Repository
@Transactional
public class RouterDeviceLogDao extends HibernateBaseDao<RouterDeviceLog,Integer>{

}
