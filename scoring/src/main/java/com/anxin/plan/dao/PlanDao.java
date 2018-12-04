package com.anxin.plan.dao;

import com.anxin.common.dao.BaseDao;
import com.anxin.plan.domain.PlanDO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 面试场次表
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-17 08:38:39
 */
@Mapper
public interface PlanDao extends BaseDao<PlanDO> {
    List<PlanDO> listJob();
    int batchSetTime(Map params);
}
