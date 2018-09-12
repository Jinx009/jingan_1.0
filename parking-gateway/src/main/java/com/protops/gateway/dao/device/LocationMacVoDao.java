package com.protops.gateway.dao.device;

import com.protops.gateway.domain.device.LocationMacVo;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jinx on 4/19/17.
 */
@Repository
public class LocationMacVoDao extends HibernateBaseDao<LocationMacVo,Integer>{

    private static final String BASE_SQL = " SELECT a.id AS location,b.area_id AS area,b.mac,b.lot_no AS lotNo FROM tbl_location a,tbl_sensor b,tbl_area c WHERE c.location_id = a.id AND b.area_id = c.id AND a.rec_st = 1 AND a.app_info_id = ";

    public List<LocationMacVo> getByAppId(Integer appInfoId){
        String sql = BASE_SQL+appInfoId;
        return findBySql(sql,LocationMacVo.class);
    }

}
