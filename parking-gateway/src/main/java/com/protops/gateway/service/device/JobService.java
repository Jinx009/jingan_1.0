package com.protops.gateway.service.device;

import com.protops.gateway.constants.enums.iot.JobStatus;
import com.protops.gateway.dao.JobDao;
import com.protops.gateway.domain.iot.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobDao jobDao;

    public Job getById(Integer id){
        return jobDao.findById(id);
    }

    public Job save(Job job){
        job.setCreateTime(new Date());
        job.setStatus(0);
        job.setRecSt(1);
        return jobDao.saveNew(job);
    }

    public List<Job> getRunningJobByRouter(List<JobStatus> jobStatuses, String routerMac) {

        List<Integer> ids = new ArrayList<Integer>();
        for(JobStatus jobStatus : jobStatuses){

            ids.add(jobStatus.getStatus());

        }

        return jobDao.getByStatusAndMac(ids,routerMac);
    }


}
