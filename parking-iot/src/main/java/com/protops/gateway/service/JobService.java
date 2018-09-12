package com.protops.gateway.service;

import com.protops.gateway.constants.enums.iot.JobStatus;
import com.protops.gateway.dao.JobDao;
import com.protops.gateway.domain.iot.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damen on 2016/4/1.
 */
@Service
@Transactional
public class JobService {

    @Autowired
    JobDao jobDao;

    public Job getAvailableJobByMac(String mac) {
        List<Job> jobs = getByMac(mac, JobStatus.getUndoingStatus());
        if (jobs == null || jobs.size() == 0) {
            return null;
        }
        return jobs.get(0);
    }

    public List<Job> getByMac(String mac, List<JobStatus> jobStatuses) {

        List<Integer> ids = new ArrayList<Integer>();
        for (JobStatus jobStatus : jobStatuses) {

            ids.add(jobStatus.getStatus());

        }

        return jobDao.getByMac(mac, ids);
    }

    public Job getById(Integer jobId) {
        return jobDao.get(jobId);
    }

    public void updateStatus(Integer id, JobStatus jobStatus) {
        jobDao.update(id, jobStatus.getStatus());
    }


    public void save(Job job) {
        jobDao.save(job);
    }
}
