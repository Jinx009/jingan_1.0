package com.protops.gateway.service.device;

import com.protops.gateway.dao.device.CarNumViewVoDao;
import com.protops.gateway.domain.device.CarNumViewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jinx on 3/31/17.
 */
@Service
public class CarNumViewVoService {

    @Autowired
    private CarNumViewVoDao carNumViewVoDao;

    public List<CarNumViewVo> getData(Integer location,Integer area,String startDate,String endDate){
        return carNumViewVoDao.getData(location,area,startDate,endDate);
    }

}
