package com.protops.gateway.dao;

import com.protops.gateway.domain.AreaVo;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by damen on 2016/3/29.
 */
@Repository
public class AreaVoDao extends HibernateBaseDao<AreaVo, Integer> {

    public AreaVo getUniqueByAreaId(Integer areaId) {
        String hql = "select a.charge_policy_id as chargePolicyId, s.area_id as id, a.name as name, a.description as `desc`, count(*) as availableCnt from tbl_sensor as s, tbl_area as a where s.rec_st = 1 and s.available = 0 and s.area_id = ? and a.id = s.area_id group by s.area_id";
        List<AreaVo> ret = findBySql(hql, AreaVo.class, areaId);
        if (ret == null || ret.size() == 0) {
            return null;
        }
        return ret.get(0);
    }

    public List<AreaVo> getGoupByAreaId(List<Integer> ids) {
        String hql = "select a.longitude as longitude, a.latitude as latitude, s.area_id as id, a.name as name, a.description as `desc`, count(*) as availableCnt from tbl_sensor as s, tbl_area as a where s.rec_st = 1 and s.available = 0 and s.area_id in (:areaList) and a.id = s.area_id group by s.area_id";
        return findObjectsBySqlWithList(hql, AreaVo.class, "areaList", ids);

    }

    public List<AreaVo> getCntGroupByArea() {
        String hql = "select area_id as id, count(*) as totalCnt from tbl_sensor where rec_st = 1 group by area_id";
        return findBySql(hql, AreaVo.class);


    }
}
