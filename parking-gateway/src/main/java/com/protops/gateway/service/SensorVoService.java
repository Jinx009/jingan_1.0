package com.protops.gateway.service;

import com.protops.gateway.dao.SensorDao;
import com.protops.gateway.dao.SensorVoDao;
import com.protops.gateway.util.Page;
import com.protops.gateway.vo.ge.SensorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fanlin on 16/7/5.
 */

@Service
@Transactional
public class SensorVoService {

    @Autowired
    SensorVoDao sensorVoDao;

    @Autowired
    SensorDao sensorDao;

    public SensorVo getPreview(Integer id) {

        return sensorVoDao.getPreview(id);
    }

    public void batchUpdate(List<Integer> sensorList, Integer areaId) {

        List<Object[]> dataSet = new ArrayList<Object[]>();

        for (Integer id : sensorList) {

            Object[] obj = new Object[1];
            obj[0] = id;
            dataSet.add(obj);
        }

        sensorDao.batchUpdateArea(areaId, dataSet);
    }


    public Page<SensorVo> pagedList(Page<SensorVo> page, Map<String, String> searchMap) {

        if(!searchMap.isEmpty()) {

            page.setResult(sensorVoDao.pagedList(page, searchMap).getResult());
            page.setTotalCount(sensorVoDao.getTotalCount(searchMap));
        } else {
            page.setResult(sensorVoDao.pagedList(page).getResult());
            page.setTotalCount(sensorVoDao.getTotalCount());
        }
        return page;
    }

}
