package com.protops.gateway.service.common;

import com.protops.gateway.dao.common.CommonVoDao;
import com.protops.gateway.domain.common.CommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jinx on 3/28/17.
 */
@Service
public class CommonVoService {

    @Autowired
    private CommonVoDao commonVoDao;

    public List<CommonVo> findIntersectionLidByArea(Integer area){
        return commonVoDao.findIntersectionLidByArea(area);
    }

    public List<CommonVo> findIntersectionPosByArea(Integer area){
        return commonVoDao.findIntersectionPosByArea(area);
    }
}
