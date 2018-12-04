package com.anxin.tempuser.dao;

import com.anxin.common.dao.BaseDao;
import com.anxin.tempuser.domain.JobTempuserDO;
import com.anxin.tempuser.domain.TempuserDO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 面试官临时账户表
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-16 19:53:36
 */
@Mapper
public interface TempuserDao extends BaseDao<TempuserDO> {
    //根据临时用户id，删除所有岗位
    int removeJobs(String tempuserid);
    //往ax_jobtempuser[临时用户岗位关联表]，插入临时用户和岗位多对多对应
    int saveJobTempuser(List<JobTempuserDO> jobTempuser);
    //根据临时用户id，查询所有岗位
    List<JobTempuserDO> listJobTempByid(String tempuserid);
    //查询用户自增数的最大值
    List<Integer> maxUserNum();

    List<TempuserDO> getByname(Map<String, Object> map);

}
