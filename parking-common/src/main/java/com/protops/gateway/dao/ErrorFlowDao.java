package com.protops.gateway.dao;

import com.protops.gateway.constants.DeviceErrorMapping;
import com.protops.gateway.domain.iot.ErrorFlow;
import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.util.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhouzhihao on 2015/4/1.
 */
@Repository
public class ErrorFlowDao extends HibernateBaseDao<ErrorFlow, Integer> {


    public List<ErrorFlow> findNoArea(){
        String hql = " FROM ErrorFlow WHERE recSt = 1 AND createTime >'2017-11-04 00:00:00' AND areaId =NULL ";
        List<ErrorFlow> list =  find(hql);
        if(list!=null&&!list.isEmpty()){
            return list;
        }
        return null;
    }

    public ErrorFlow isLost(String mac){
        String hql = " FROM ErrorFlow WHERE recSt = 1 AND errorBitMap = '12290' AND mac = ?  AND status = 0 ";
        List<ErrorFlow> list =  find(hql,mac);
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public ErrorFlow getBySerialNo(String serialNo) {
        String hql = "from ErrorFlow where serialNo = ?";
        return findUnique(hql, serialNo);
    }

    public Page<ErrorFlow> pagedList(Page<ErrorFlow> page,Integer areaId) {
        String hql = "from ErrorFlow where recSt = 1 and areaId = "+areaId+" order by id desc";
        return find(page, hql);
    }

    public int getTotalCount(Integer areaId) {

        String hql = "select count(*) from ErrorFlow where recSt = 1 and areaId ="+areaId+" ";

        return findLong(hql).intValue();

    }

    public Page<ErrorFlow> pagedList(Page<ErrorFlow> page) {
        String hql = "from ErrorFlow where recSt = 1 order by id desc";
        return find(page, hql);
    }

    public int getTotalCount() {

        String hql = "select count(*) from ErrorFlow where recSt = 1";

        return findLong(hql).intValue();

    }

    public int pagedListWithAreaIdTotalCount(Integer areaId) {
        String hql = "select count(*) from ErrorFlow where recSt = 1 and areaId = "+areaId+" and errorBitMap in ("+ DeviceErrorMapping.mapListStr+")";
        return  findLong(hql).intValue();
    }

    public Page<ErrorFlow> pagedListWithAreaId(Page<ErrorFlow> page,Integer areaId) {
        String hql = "from ErrorFlow where recSt = 1 and areaId = "+areaId+" and errorBitMap in ("+ DeviceErrorMapping.mapListStr+") order by id desc";
        return find(page, hql);
    }

    public Page<ErrorFlow> pagedListWithError(Page<ErrorFlow> page) {
        String hql = "from ErrorFlow where recSt = 1 and status = 0 and errorBitMap in ("+ DeviceErrorMapping.mapListStr+") order by id desc";
        return find(page, hql);
    }

    public int getTotalCountWithError() {

        String hql = "select count(*) from ErrorFlow where recSt = 1 and status = 0 ";

        return findLong(hql).intValue();

    }
}
