package com.anxin.post.service;

import com.anxin.common.service.BaseService;
import com.anxin.post.domain.PostDO;

import java.util.List;

/**
 * 岗位表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-16 19:35:42
 */
public interface PostService extends BaseService<PostDO> {

    List<PostDO> getJobs(String userid);

    PostDO getJobInfo(String jobid);
}
