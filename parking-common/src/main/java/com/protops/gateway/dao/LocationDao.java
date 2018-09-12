package com.protops.gateway.dao;

import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.util.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by damen on 2016/3/29.
 */
@Repository
public class LocationDao extends HibernateBaseDao<Location, Integer> {

    public List<Location>  findNeedNotice(){
        String hql = " FROM Location WHERE recSt = 1 AND (noticeType = 1 OR noticeType = 3) ";
        return find(hql);
    }

    public List<Location> findNeedDeviceNotice(){
        String hql = " FROM Location WHERE recSt = 1 AND (noticeType = 2 OR noticeType = 3) ";
        return find(hql);
    }

    public List<Location> findByAppInfoId(Integer appInfoId){
        String hql = " SELECT a.id,a.name FROM Location a WHERE a.appInfoId = ? ";
        return  find(hql,appInfoId);
    }

    public List<Location> findOpenByAppInfoId(Integer appInfoId){
        String hql = " FROM Location a WHERE a.appInfoId = ? ";
        return  find(hql,appInfoId);
    }

    public Page<Location> pagedList(Page<Location> page) {
        String hql = "from Location where recSt = 1 order by id desc";
        return find(page, hql);
    }

    public int getTotalCount() {

        String hql = "select count(*) from Location where recSt = 1";

        return findLong(hql).intValue();

    }

    public List<Location> findLocationByLevel(int level) {

        String hql = "from Location where level = ? and parentId is null";

        return find(hql, level);

    }

    public List<Location> getListByName(String location) {

        String hql = "from Location where recSt = 1 and name like ?";

        return find(hql, '%' + location + '%');
    }

    public List<Location> getByTag(String district) {
        String hql = "from Location where tag = ? and recSt = 1";
        return find(hql, district);
    }

    public List<Location> getByIds(List<Integer> ids) {
        String hql = "from Location where id in (:ids) and recSt = 1";
        return findIn(hql, "ids", ids);

    }
}
