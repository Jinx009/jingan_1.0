package com.protops.gateway.dao.device;

import com.protops.gateway.domain.device.IntersectionSensor;
import com.protops.gateway.util.HibernateBaseDao;
import com.protops.gateway.util.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jinx on 3/13/17.
 */
@Repository
@Transactional
public class IntersectionSensorDao extends HibernateBaseDao<IntersectionSensor,Integer>{

    /**
     * 通过mac查找
     * @param mac
     * @return
     */
    public IntersectionSensor findByMac(String mac){
        String hql = " FROM IntersectionSensor WHERE mac = ? ";
        return findUnique(hql,mac);
    }

    public List<IntersectionSensor> findByPosAndArea(Integer area){
        String hql = " SELECT a.lid FROM IntersectionSensor a WHERE  a.area = ? ";
        return find(hql,area);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    public Page<IntersectionSensor> pagedList(Page<IntersectionSensor> page) {
        String hql = " FROM IntersectionSensor WHERE recSt = 1 ORDER BY id DESC";
        return find(page, hql);
    }

    /**
     * 获取总数
     * @return
     */
    public int getTotalCount() {
        String hql = "SELECT count(*) FROM IntersectionSensor WHERE recSt = 1";
        return findLong(hql).intValue();

    }

}
