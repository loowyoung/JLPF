package com.anxin.post.dao;

import com.anxin.common.dao.BaseDao;
import com.anxin.post.domain.PostDO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 岗位表
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-16 19:35:42
 */
@Mapper
public interface PostDao extends BaseDao<PostDO> {

    List<PostDO> getJobs(String userid);

    PostDO getJobInfo(String jobid);
}
