package com.protops.gateway.Job;

import com.protops.gateway.dao.AreaVoDao;
import com.protops.gateway.domain.AreaVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by doubleminter on 2016/4/13.
 */
public class AreaJob {

    @Autowired
    AreaVoDao areaVoDao;

    private HashMap<Integer, BigInteger> cntMap = new HashMap<Integer, BigInteger>();

    public BigInteger getCnt(Integer areaId){
        return cntMap.get(areaId);
    }

    public Integer getTotalCnt(Integer areaId){
        return getCnt(areaId).intValue();
    }

    public void process() {

        HashMap<Integer, BigInteger> cntMapTemp = new HashMap<Integer, BigInteger>();

        List<AreaVo> areaCntList = areaVoDao.getCntGroupByArea();

        Iterator iter = areaCntList.iterator();

        while (iter.hasNext()) {

            AreaVo areaCnt = (AreaVo) iter.next();

            cntMapTemp.put(areaCnt.getId(), areaCnt.getTotalCnt());

        }

        cntMap = cntMapTemp;

    }

}
