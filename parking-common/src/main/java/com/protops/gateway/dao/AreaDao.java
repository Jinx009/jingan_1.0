package com.protops.gateway.dao;

import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.domain.iot.Router;
import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.util.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by damen on 2016/3/29.
 */
@Repository
@Transactional
public class AreaDao extends HibernateBaseDao<Area, Integer> {


    public List<Area> getBaseAreasByLocationId(Integer id){
        String hql = " SELECT a.name,a.id FROM Area a WHERE a.locationId = ? ";
        return find(hql,id);
    }

    public Page<Area> pagedList(Page<Area> page) {
        String hql = "from Area where recSt = 1 order by id desc";
        return find(page, hql);
    }

    public int getTotalCount() {

        String hql = "select count(*) from Area where recSt = 1";

        return findLong(hql).intValue();

    }

    public List<Area> getAreasByLocationId(Integer locationId) {
        String hql = "from Area where locationId = ?";
        return find(hql, locationId);
    }

    public List<Area> getByDistrict(String district) {
        String hql = "from Area where tag = ?";
        return find(hql, district);
    }

    public List<Area> getListByName(String area) {

        String hql = "from Area where recSt = 1 and name like ?";

        return find(hql, '%' + area + '%');
    }

}
