package com.anxin.post.service.impl;

import com.anxin.common.service.impl.BaseImpl;
import org.springframework.stereotype.Service;

import com.anxin.post.dao.PostDao;
import com.anxin.post.domain.PostDO;
import com.anxin.post.service.PostService;

import java.util.List;


@Service
public class PostServiceImpl extends BaseImpl<PostDao,PostDO> implements PostService {

    @Override
    public List<PostDO> getJobs(String userid) {
        return dao.getJobs(userid);
    }

    @Override
    public PostDO getJobInfo(String jobid) {
        return dao.getJobInfo(jobid);
    }
}
