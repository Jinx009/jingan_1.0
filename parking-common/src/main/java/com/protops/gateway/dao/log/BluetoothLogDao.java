package com.protops.gateway.dao.log;

import com.protops.gateway.domain.log.BluetoothLog;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jinx on 6/23/17.
 */
@Transactional
@Repository("bluetoothLogDao")
public class BluetoothLogDao extends HibernateBaseDao<BluetoothLog,Integer> {

    public List<BluetoothLog> findByRouterMac(String routerMac){
        String sql = " SELECT a.uuid_value AS uuid,a.report_time AS reportTime,a.signal_value AS signalValue "+
                " FROM tbl_bluetooth_log a WHERE a.report_time = "+
                " (SELECT DISTINCT report_time FROM tbl_bluetooth_log WHERE router_mac =? AND STATUS = 3 AND report_time IS NOT NULL ORDER BY report_time DESC LIMIT 1)";
        return findBySql(sql,BluetoothLog.class,routerMac);
    }

}
