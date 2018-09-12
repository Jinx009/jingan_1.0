package com.protops.gateway.dao;

import com.protops.gateway.domain.iot.Job;
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
public class JobDao extends HibernateBaseDao<Job, Integer> {

    public Page<Job> pagedList(Page<Job> page) {
        String hql = "from Job where recSt = 1 order by id desc";
        return find(page, hql);
    }

    public int getTotalCount() {

        String hql = "select count(*) from Job where recSt = 1";

        return findLong(hql).intValue();

    }

    public Job saveNew(Job job){
        save(job);
        return job;
    }

    public Job findById(Integer id){
        String hql = " FROM Job WHERE id = ? ";
        return findUnique(hql,id);
    }

    public Job getByMac(String mac) {
        String hql = "from Job where mac=? and recSt = 1";
        return findUnique(hql, mac);
    }

    public List<Job> getByMac(String mac, List<Integer> status) {
        String hql = "from Job where target=? and recSt = 1 and status in (:status) order by id desc";
        List<Job> jobs = findIn(hql, "status", status, mac);
        if (jobs == null || jobs.size() == 0) {
            return null;
        }
        return jobs;
    }

    public List<Job> getByStatus(List<Integer> status) {
        String hql = "from Job where recSt = 1 and status in (:status) order by id desc";
        List<Job> jobs = findIn(hql, "status", status);
        if (jobs == null || jobs.size() == 0) {
            return null;
        }
        return jobs;
    }

    public List<Job> getByStatusAndMac(List<Integer> status,String routerMac) {
        String hql = "from Job where recSt = 1 and status in (:status) and target ='"+routerMac+"' order by id desc";
        List<Job> jobs = findIn(hql, "status", status);
        if (jobs == null || jobs.size() == 0) {
            return null;
        }
        return jobs;
    }

    public void update(Integer id, Integer status) {
        String hql = "update Job set status = ? where id = ?";
        update(hql, status, id);
    }
}
