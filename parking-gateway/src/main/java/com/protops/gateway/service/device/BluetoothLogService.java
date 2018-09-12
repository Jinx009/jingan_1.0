package com.protops.gateway.service.device;

import com.protops.gateway.dao.log.BluetoothLogDao;
import com.protops.gateway.domain.log.BluetoothLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jinx on 6/23/17.
 */
@Service
public class BluetoothLogService {

    @Autowired
    private BluetoothLogDao bluetoothLogDao;

    public List<BluetoothLog> findByRouterMac(String mac){
        return bluetoothLogDao.findByRouterMac(mac);
    }

}
