package com.protops.gateway.dao;

import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.util.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by damen on 2016/3/29.
 */
@Repository
public class SensorDao extends HibernateBaseDao<Sensor, Integer> {

    public List<Sensor> getNeedNotice(Location location){
        String sql = "SELECT a.id AS id,a.last_seen_time AS lastSeenTime ,a.mac AS mac FROM tbl_sensor a WHERE a.area_id IN(SELECT id FROM tbl_area WHERE location_id = ?) AND a.rec_st = 1";
        return findBySql(sql,Sensor.class,location.getId());
    }

    public Sensor getByDescAndAreaId(Integer areaId,String desc){
        String sql = "select a.id,a.mac,a.available  from tbl_sensor a where a.area_id = ? and a.description = ? ";
        List<Sensor> list =  findBySql(sql,Sensor.class,areaId,desc);
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public Page<Sensor> pagedList(Page<Sensor> page) {
        String hql = "from Sensor where recSt = 1 order by id desc";
        return find(page, hql);
    }

    public int getTotalCount() {

        String hql = "select count(*) from Sensor where recSt = 1";

        return findLong(hql).intValue();

    }

    public Sensor getByMac(String mac) {
        String hql = "from Sensor where mac=? and recSt = 1";
        return findUnique(hql, mac);
    }

    public List<Sensor> getSensorsByStatus(Integer areaId, int status) {
        String hql = "from Sensor where recSt = 1 and areaId = ? and available = ?";
        return find(hql, areaId, status);
    }

    public List<Sensor> getSensorsByArea(Integer areaId){
        String hql = "from Sensor where recSt = 1 and areaId = ? order by description";
        return find(hql, areaId);
    }

    public void batchUpdateArea(Integer areaId, List<Object[]> obj) {

        String sql = "update tbl_sensor set area_id = " + areaId + " where id = ? ";

        batchUpdate(sql, obj);
    }

    public Sensor getByParentMac(String parentMac){
        String hql = " FROM Sensor WHERE parentMac = ? ";
        List<Sensor> list = find(hql,parentMac);
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
