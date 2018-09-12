package com.protops.gateway.dao;

import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.util.Page;
import com.protops.gateway.vo.ge.SensorVo;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by fanlin on 16/7/5.
 */
@Repository
public class SensorVoDao extends HibernateBaseDao<SensorVo, Integer> {

    private static final String selSql = "select sensor.hardware_version as hardwareVersion,sensor.bluetooth AS bluetooth,sensor.mode as mode, sensor.id as id,sensor.mac as mac,sensor.uid as uid,sensor.description as description, sensor.firmware_version as firmwareVersion,sensor.battery_voltage as batteryVoltage " +
            ",   sensor.x_mag as xMag, sensor.y_mag as yMag, sensor.z_mag as zMag,sensor.available as available,sensor.base_energy as baseEnergy,sensor.heartbeat_interval as heartBeatInterval " +
            ",   sensor.channel_id as channelId, sensor.pan_id as panId, sensor.frequncy as frequency, sensor.parent_mac as parentMac, sensor.router_mac as routerMac, sensor.connected as connected " +
            ",   sensor.type as type, sensor.area_id as areaId, sensor.last_seen_time as lastSeenTime, sensor.longitude as longitude, sensor.latitude as latitude " +
            ",   sensor.create_time as createTime, sensor.addr as addr, sensor.paid as paid, sensor.in_time as inTime, sensor.current_member_id as currentMemberId " +
            ",   sensor.expire_time as expireTime, sensor.notice_flag as noticeFlag, sensor.update_time as updateTime";
    private static final String selAreaSql = " , area.name as areaName, area.location_id as locationId, area.level as areaLevel, area.tag as areaTag, area.description as areaDescription, area.charge_policy_id as chargePolicyId";
    private static final String selLocationSql = " , location.name as locationName, location.parent_id as locationParentId, location.level as locationLevel, location.tag as locationTag" +
            ", location.description as locationDescription ";

    private static final String fromSensorSql = " FROM tbl_sensor sensor";
    private static final String joinAreaSql = " LEFT JOIN tbl_area area ON sensor.area_id = area.id ";
    private static final String joinLocationSql = " LEFT JOIN tbl_location location ON area.location_id = location.id ";

    private static final String whereSql = "where sensor.rec_st = 1";

    private static final String mainSql = selSql + selAreaSql + selLocationSql + fromSensorSql + joinAreaSql + joinLocationSql + whereSql;
    private static final String fromSql = fromSensorSql + joinAreaSql + joinLocationSql + whereSql;

    public Page<SensorVo> pagedList(Page<SensorVo> page) {

        String sql = mainSql ;

        Object[] objs = {};

        return findBySqlWithObject(page, sql, SensorVo.class, objs);
    }

    public Page pagedList(Page<SensorVo> page, Map<String, String> searchMap) {

        String sql = mainSql + dealSearchMap(searchMap);

        Object[] objs = {};

        return findBySqlWithObject(page, sql, SensorVo.class, objs);
    }


    public int getTotalCount() {

        String sql = " select count(*) " + fromSql;

        return findCountBySql(sql, new Object[0]);
    }

    public int getTotalCount(Map<String, String> searchMap) {

        String sql = " select count(*) " + fromSql + dealSearchMap(searchMap);

        return findCountBySql(sql, new Object[0]);
    }

    private String dealSearchMap(Map<String, String> searchMap){
        StringBuilder sb = new StringBuilder();

        if(!searchMap.isEmpty()){

            Iterator iterator = searchMap.keySet().iterator();
            while (iterator.hasNext()){
                String key = (String)iterator.next();
                String value = searchMap.get(key);

                if(key.equals("connected"))
                    sb.append(" and sensor.connected = " + value);
                if(key.equals("available"))
                    sb.append(" and sensor.available = " + value);
                if(key.equals("area"))
                    sb.append(" and area.id = " + value);
                if(key.equals("location"))
                    sb.append(" and location.id = " + value);
                if(key.equals("mac"))
                    sb.append(" and sensor.mac like '%" + value + "%' ");

                sb.append(" order by sensor.description ");
            }
        }

        return sb.toString();
    }

    public SensorVo getPreview(Integer id) {

        String sql = mainSql + " and sensor.id = ?";

        List<SensorVo> list = findBySql(sql, SensorVo.class, id);

        if(list.size() > 0)
            return list.get(0);
        else
            return null;
    }



}
