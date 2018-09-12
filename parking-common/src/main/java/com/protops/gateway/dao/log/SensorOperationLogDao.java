package com.protops.gateway.dao.log;

import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.domain.log.SensorOperationLog;
import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jinx on 3/24/17.
 */
@Repository
@Transactional
public class SensorOperationLogDao extends HibernateBaseDao<SensorOperationLog,Integer>{
    private static final Logger logger = LoggerFactory.getLogger(SensorOperationLogDao.class);

    public List<SensorOperationLog> findByAreaId(Integer areaId,String dateStr){
        String hql = " FROM SensorOperationLog WHERE changeTime > '"+dateStr+" 00:00:00' AND changeTime < '"+dateStr+" 23:59:59'  AND areaId = "+areaId+"  ORDER BY id DESC ";
        return find(hql);
    }

    public List<SensorOperationLog> findByAreaId(Integer areaId,String dateStr,String mac){
        String hql = " FROM SensorOperationLog WHERE changeTime > '"+dateStr+" 00:00:00' AND changeTime < '"+dateStr+" 23:59:59'  AND areaId = "+areaId+"  ORDER BY id DESC ";
        if(!"0".equals(mac)){
            hql = " FROM SensorOperationLog WHERE changeTime > '"+dateStr+" 00:00:00' AND changeTime < '"+dateStr+" 23:59:59'  AND areaId = "+areaId+" AND mac='"+mac+"'  ORDER BY id DESC ";
        }
        return find(hql);
    }

    public int nearStatus(SensorOperationLog sensorOperationLog){//0表示正常 1 表示需要新建车出记录 2 表示不发送
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sensorOperationLog.getCreateTime());
            calendar.add(Calendar.HOUR,-24);
            Date date = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(date);
            String sql = " SELECT id,change_time AS changeTime FROM tbl_sensor_operationlog WHERE mac = '"+sensorOperationLog.getMac()+
                    "'  AND create_time> '"+dateStr+"' AND send_status = 1 AND fail_times <4 ORDER BY id DESC ";
            List<SensorOperationLog> list = findBySql(sql,SensorOperationLog.class);
            if(list!=null&&!list.isEmpty()){
                SensorOperationLog sensorOperationLog1 = list.get(0);
                logger.warn("i am comming...{}",sensorOperationLog1);
                sensorOperationLog1 = get(sensorOperationLog1.getId());
                if(0==sensorOperationLog1.getAvailable()){
                    return 0;
                }else{
                    long diff = sensorOperationLog.getChangeTime().getTime() - sensorOperationLog1.getChangeTime().getTime();
                    long seconds = diff / (1000);
                    logger.warn("diff:{}",diff);
                    if(seconds<30){
                        return 2;
                    }else{
                        return 1;
                    }
                }
            }
        }catch (Exception e){
            logger.error("error:{}",e);
        }
        return 0;
    }

    public SensorOperationLog getNearIn(String mac){
        String sql = " SELECT id,change_time AS changeTime,mac AS mac FROM tbl_sensor_operationlog WHERE mac = '"+mac+"' AND available = 1 ORDER BY change_time DESC LIMIT 1  ";
        List<SensorOperationLog> list = findBySql(sql,SensorOperationLog.class);
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
    public SensorOperationLog getNearOut(String mac){
        String sql = " SELECT id,change_time AS changeTime,mac AS mac FROM tbl_sensor_operationlog WHERE mac = '"+mac+"' AND available = 0 ORDER BY change_time DESC LIMIT 2  ";
        List<SensorOperationLog> list = findBySql(sql,SensorOperationLog.class);
        if(list!=null&&!list.isEmpty()){
            if(list.size()==1){
                return list.get(0);
            }else{
                return list.get(1);
            }
        }
        return null;
    }

    public SensorOperationLog findByLogIdAndStatus(Sensor sensor){
        if(StringUtils.isNotBlank(sensor.getLogId())){
            String hql = " FROM  SensorOperationLog WHERE logId = ? AND available = ?";
            return findUnique(hql,sensor.getLogId(),sensor.getAvailable());
        }else{
            return null;
        }
    }

    /**
     * 2017-04-17添加每次车进车出添加流水id
     */
    public List<SensorOperationLog> getByLocationId(String ids){
        String sql = " SELECT a.available AS available,a.description AS description,a.id AS id,a.change_time AS changeTime,a.mac AS mac,a.log_id AS logId,a.fail_times AS failTimes,a.send_time AS sendTime FROM tbl_sensor_operationlog a WHERE a.area_id IN ("+ids+")  AND a.send_status = 0 AND a.fail_times <4 AND a.change_time!='1970-01-01 00:00:00' ORDER BY id ";
        List<SensorOperationLog> list = findBySql(sql,SensorOperationLog.class);
        List<SensorOperationLog> nowList = new ArrayList<SensorOperationLog>();
        if(list!=null&&!list.isEmpty()){
            logger.warn("data before send{}", JsonUtil.toJson(list));
            Date date = new Date();
            //过滤掉历史数据 时差超过30分钟的数据
            for(SensorOperationLog s : list){
                long interval = (date.getTime() - s.getChangeTime().getTime())/1000;
                if(interval>1800){
                    s = get(s.getId());
                    s.setSendStatus(1);
                    s.setSendTime(new Date());
                    s.setFailTimes(10);
                    update(s);
                }else{
                    nowList.add(s);
                }
            }
        }
        Map<String,SensorOperationLog> map = new LinkedHashMap<String, SensorOperationLog>();
        //Mac用于检索不重复数据的条目
        for(SensorOperationLog sensorOperationLog:nowList){
            map.put(sensorOperationLog.getLogId()+sensorOperationLog.getAvailable()+sensorOperationLog.getMac(),sensorOperationLog);
        }
        List<SensorOperationLog> listValue = new ArrayList<SensorOperationLog>();
        List<SensorOperationLog> sendValue = new ArrayList<SensorOperationLog>();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            listValue.add(map.get(key));
        }
        //删掉相隔时间较近的同状态数据
        for(SensorOperationLog sensorOperationLog:listValue){
            List<SensorOperationLog> lists = getBySameLogIdList(sensorOperationLog);
            if(lists!=null&&!lists.isEmpty()){
                sensorOperationLog = get(sensorOperationLog.getId());
                sensorOperationLog.setSendStatus(1);
                sensorOperationLog.setSendTime(new Date());
                sensorOperationLog.setFailTimes(7);
                update(sensorOperationLog);
                continue;
            }else{
                sendValue.add(sensorOperationLog);
            }
        }
        //删除检索不重复中重复的数据
        for(SensorOperationLog sensorOperationLog:nowList){
            int status = 0;
            for(SensorOperationLog sensorOperationLog1:sendValue){
                if(sensorOperationLog1.getId().equals(sensorOperationLog.getId())){
                    status = 1;
                    continue;
                }
            }
            if(status==0){
                sensorOperationLog = get(sensorOperationLog.getId());
                sensorOperationLog.setSendStatus(1);
                sensorOperationLog.setSendTime(new Date());
                sensorOperationLog.setFailTimes(7);
                update(sensorOperationLog);
            }
        }
        if(sendValue!=null&&!sendValue.isEmpty()){
            logger.warn("trans after for send data{}", JsonUtil.toJson(sendValue));
        }
        return sendValue;
    }

    public List<SensorOperationLog> getByLocationIdHour(Integer locationId){
        String sql = " SELECT a.available AS available,a.id AS id,a.change_time AS changeTime,a.mac AS mac,a.log_id AS logId,a.fail_times AS failTimes,a.send_time AS sendTime FROM tbl_sensor_operationlog a WHERE a.mac IN (SELECT mac FROM tbl_sensor WHERE  rec_st = 1 AND area_id IN (SELECT id FROM tbl_area WHERE rec_st = 1 AND location_id = ?))  AND a.send_status = 0 AND a.fail_times >3 ";
        return findBySql(sql,SensorOperationLog.class,locationId);
    }

    public List<SensorOperationLog> getBySameLogIdList(SensorOperationLog sensorOperationLog){
        String hql = " FROM  SensorOperationLog WHERE  mac = ? AND logId = ? AND sendStatus =1 AND available =? ORDER BY changeTime DESC";
        List<SensorOperationLog> list = find(hql,sensorOperationLog.getMac(),sensorOperationLog.getLogId(),sensorOperationLog.getAvailable());
        if(list!=null&&!list.isEmpty()){
            return list;
        }
        return null;
    }

    public SensorOperationLog getBySameLogId(SensorOperationLog sensorOperationLog){
        String hql = " FROM  SensorOperationLog WHERE  mac = ? AND sendStatus =1 ORDER BY changeTime DESC";
        List<SensorOperationLog> list = find(hql,sensorOperationLog.getMac());
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

}
