package com.anxin.tempuser.domain;

import com.anxin.common.domain.BaseEntity;

/**
 * 临时用户岗位关联表
 */
public class JobTempuserDO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    //临时用户id
    private String tempuserid;
    //岗位id
    private String jobid;

    public String getTempuserid() {
        return tempuserid;
    }

    public String getJobid() {
        return jobid;
    }

    public void setTempuserid(String tempuserid) {
        this.tempuserid = tempuserid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }
}
