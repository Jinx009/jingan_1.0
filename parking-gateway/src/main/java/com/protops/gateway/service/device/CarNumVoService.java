package com.protops.gateway.service.device;

import com.protops.gateway.dao.device.CarNumVoDao;
import com.protops.gateway.domain.device.CarNumVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jinx on 3/29/17.
 */
@Service
public class CarNumVoService {

    @Autowired
    private CarNumVoDao carNumVoDao;

    public List<CarNumVo> getCarNumOneDay(Integer location,Integer area,Integer lid,String dateStr){
        return carNumVoDao.getCarNumOneDay(location,area,lid,dateStr);
    }

    public List<CarNumVo> getCarNumManyDay(Integer location,Integer area,Integer lid,String dateStr,String endDate){
        return carNumVoDao.getCarNumManyDay(location,area,lid,dateStr,endDate);
    }
}
