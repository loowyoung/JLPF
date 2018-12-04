package com.anxin.applicants.service;

import com.anxin.applicants.domain.ApplicantsDO;
import com.anxin.common.service.BaseService;
import com.anxin.common.utils.Query;
import com.anxin.post.domain.PostDO;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-15 17:09:07
 */
public interface ApplicantsService extends BaseService<ApplicantsDO> {
    //候选人表和打分表的综合查询
//    List<ApplicantsDO> preadmissionlist(Map<String, Object> map);
    //查询候选人的所有岗位
    List<ApplicantsDO> listType();
    //更新分数信息
    int updateScore(ApplicantsDO entity);

    List<ApplicantsDO> getExpotList(Map<String, Object> mapSql);

    List<ApplicantsDO> exportlist(Map<String, Object> m);

    List<ApplicantsDO> getExcellents(String userid);

    List<ApplicantsDO> getAppicants(String jobid,String userid);

    //查询限定候选人面试总分、评委总数等
    List<ApplicantsDO> preadmissionLimit(Map<String, Object> map);

    //查询候选人面试总分、评委总数等
    List<ApplicantsDO> preadmissionAll(Map<String, Object> map);

    //查询限定候选人总数
    int countPreAdmission(Query query);

    //设置候选人的录取类型
    void setAdmissionType();

    List<ApplicantsDO> getInterviewResult(Map<String, Object> map);

    ApplicantsDO getByNameAndJob(String username, String job);
}
