package com.protops.gateway.domain.iot;

import com.protops.gateway.constants.enums.iot.JobStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by damen on 2016/3/30.
 */
@Entity
@Table(name = "tbl_job")
public class Job implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "cmd")
    private String cmd;

    @Column(name = "target")
    private String target;

    @Column(name = "status")
    private Integer status;

    @Column(name = "job_detail")
    private String jobDetail;

    @Column(name = "timeout")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeout;

    @Column(name = "rec_st")
    private Integer recSt = 1;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "info")
    private String info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRecSt() {
        return recSt;
    }

    public void setRecSt(Integer recSt) {
        this.recSt = recSt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(String jobDetail) {
        this.jobDetail = jobDetail;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getStatusDisplay() {
        return JobStatus.parse(getStatus()).getName();
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Date getTimeout() {
        return timeout;
    }

    public void setTimeout(Date timeout) {
        this.timeout = timeout;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
