package com.protops.gateway.dao.common;

import com.protops.gateway.domain.common.CommonVo;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jinx on 3/28/17.
 */
@Repository
public class CommonVoDao extends HibernateBaseDao<CommonVo,Integer> {

    public List<CommonVo> findIntersectionLidByArea(Integer area){
        String sql = " SELECT DISTINCT lid AS value FROM tbl_intersectionsensor WHERE area_id =? ";
        return findBySql(sql,CommonVo.class,area);
    }

    public List<CommonVo> findIntersectionPosByArea(Integer area){
        String sql = " SELECT DISTINCT pos AS value FROM tbl_intersectionsensor WHERE area_id =? ";
        return findBySql(sql,CommonVo.class,area);
    }

}
