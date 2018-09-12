package com.protops.gateway.dao;

import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.domain.iot.Router;
import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.util.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by damen on 2016/3/29.
 */
@Repository
@Transactional
public class RouterDao extends HibernateBaseDao<Router, Integer> {

    public Page<Router> pagedList(Page<Router> page) {
        String hql = "from Router where recSt = 1 order by id desc";
        return find(page, hql);
    }

    public List<Router> getNeedNotice(Location location){
        String sql = " SELECT a.id AS id,a.last_seen_time AS lastSeenTime,a.mac as mac FROM tbl_router a WHERE a.location_id = ?  AND a.rec_st = 1 ";
        return findBySql(sql,Router.class,location.getId());
    }


    public int getTotalCount() {

        String hql = "select count(*) from Router where recSt = 1";

        return findLong(hql).intValue();

    }

    public Router getByMac(String mac) {
        String hql = "from Router where mac=? and recSt = 1";
        return findUnique(hql, mac);
    }

    public Page pagedList(Page<Router> page, Map<String, String> searchMap) {

        String hql = "from Router where recSt = 1 " + dealSearchMap(searchMap) + "order by id desc";
        return find(page, hql);
    }

    private String dealSearchMap(Map<String, String> searchMap){
        StringBuilder sb = new StringBuilder();

        if(!searchMap.isEmpty()){

            Iterator iterator = searchMap.keySet().iterator();
            while (iterator.hasNext()){
                String key = (String)iterator.next();
                String value = searchMap.get(key);

                if(key.equals("mac")) {
                    sb.append(" and mac like '%" + value + "%' ");
                }
            }
        }

        return sb.toString();
    }

    public int getTotalCount(Map<String, String> searchMap) {

        String hql = "select count(*) from Router where recSt = 1" + dealSearchMap(searchMap);

        return findLong(hql).intValue();
    }

    public void updateTime(String mac, Date date) {
        String hql = "update Router set lastSeenTime = ? where mac = ?";
        update(hql, date, mac);
    }

    public void updateDesc(Integer id, String desc) {
        String hql = "update Router set note = ? where id = ?";
        update(hql, desc, id);
    }

    public int getTotalCount(List<Integer> ids) {

        String hql = "select count(*) from Router where recSt = 1 and id in (:ids)";

        return findLongIn(hql, "ids", ids).intValue();

    }

    public Page pagedList(Page<Router> page, List<Integer> ids) {
        String hql = "from Router where recSt = 1 and id in (:ids) order by id desc";
        return findInPage(page, hql, "ids", ids);
    }
}
