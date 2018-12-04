package com.anxin.applicants.dao;

import com.anxin.applicants.domain.ApplicantsDO;

import com.anxin.common.dao.BaseDao;
import com.anxin.common.utils.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-15 17:09:07
 */
@Mapper
public interface ApplicantsDao extends BaseDao<ApplicantsDO> {
    //SELECT a.*,sum(b.score),COUNT(b.id) from ax_applicants a, ax_score b WHERE a.id=b.applicant_id
    List<ApplicantsDO> preadmissionlist(Map<String, Object> map);
    List<ApplicantsDO> listType();

    int updateScore(ApplicantsDO entity);

    List<ApplicantsDO> getExportList(Map<String, Object> mapSql);

    List<ApplicantsDO> exportlist(Map<String, Object> m);

    List<ApplicantsDO> getExcellents(String userid);

    List<ApplicantsDO> getAppicants(@Param("jobid") String jobid ,@Param("userid")  String userid);

    List<ApplicantsDO> preadmissionlistlimit(Map<String, Object> map);

    int countPreAdmission(Query query);

    List<ApplicantsDO> getInterviewResult(Map<String, Object> map);

    ApplicantsDO getByNameAndJob(@Param("username") String username, @Param("job") String job);
}
