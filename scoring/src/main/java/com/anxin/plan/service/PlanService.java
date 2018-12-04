package com.anxin.plan.service;

import com.anxin.common.service.BaseService;
import com.anxin.plan.domain.PlanDO;

import java.util.List;

/**
 * 面试场次表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-17 08:38:39
 */
public interface PlanService extends BaseService<PlanDO> {
    //查询候选人的所有岗位
    List<PlanDO> listJob();
    //给选定候选人设定面试时间
    int batchSetTime(String[] ids,String starttime,String endtime);

}
