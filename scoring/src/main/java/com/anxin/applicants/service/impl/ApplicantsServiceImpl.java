package com.anxin.applicants.service.impl;

import com.anxin.applicants.service.ApplicantsService;
import com.anxin.common.service.impl.BaseImpl;
import com.anxin.common.utils.Query;
import com.anxin.post.domain.PostDO;
import com.anxin.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anxin.applicants.dao.ApplicantsDao;
import com.anxin.applicants.domain.ApplicantsDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ApplicantsServiceImpl extends BaseImpl<ApplicantsDao, ApplicantsDO> implements ApplicantsService {

    @Autowired
    private PostService postService;

    /**
     * 重设候选人录取类型
     */
    @Override
    public void setAdmissionType() {
        //查询所有岗位
        List<PostDO> postList = postService.list(null);
        //设置候选人的录取类型为空
        List<ApplicantsDO> userlist0 = dao.preadmissionlist(null);
        for (int j = 0; j < userlist0.size(); j++) {
            ApplicantsDO admissionType = new ApplicantsDO();
            admissionType.setId(userlist0.get(j).getId());
            admissionType.setAdmissiontype("");
            dao.update(admissionType);
        }
        //循环所有岗位，设置候选人的录取类型
        for (int i = 0; i < postList.size(); i++) {
            //设置候选人的录取类型为拟录取-1
            if ("".equals(postList.get(i).getQuota())) {
                continue;
            }
            //从0到拟录取人数
            updateAdmissionType(postList.get(i).getJobname(), "0", postList.get(i).getQuota(), "1");
            //设置候选人的录取类型为递补-2
            if ("".equals(postList.get(i).getCandidate()) || null == postList.get(i).getCandidate()) {
                continue;
            }
            //从拟录取人数到递补人数
            updateAdmissionType(postList.get(i).getJobname(), postList.get(i).getQuota(), postList.get(i).getCandidate(), "2");
        }
//        return preadmissionLimit(map);
    }

    @Override
    public List<ApplicantsDO> getInterviewResult(Map<String, Object> map) {

        return dao.getInterviewResult(map);
    }

    @Override
    public ApplicantsDO getByNameAndJob(String username, String job) {
        return dao.getByNameAndJob(username,job);
    }

    /**
     * 根据岗位的拟录取人数和递补人数，设置候选人的录取类型
     *
     * @param job           岗位名称
     * @param offset
     * @param limit
     * @param admissionType 录取类型 1-拟录取 2-递补
     */
    void updateAdmissionType(String job, String offset, String limit, String admissionType) {
        Map<String, Object> map = new HashMap<>();
        map.put("job", job);
        map.put("offset", offset);
        map.put("limit", limit);
        Query query = new Query(map);
        List<ApplicantsDO> userlist = dao.preadmissionlist(query);
        for (int j = 0; j < userlist.size(); j++) {
            ApplicantsDO user = new ApplicantsDO();
            user.setId(userlist.get(j).getId());
            user.setAdmissiontype(admissionType);
            dao.update(user);
        }
    }

    /**
     * 查询所有拟录取人员，同时有分数和录取类型的候选人才可以查出（admissiontype!='' and interviewresult!=''）；并按分数排列
     *
     * @param map
     * @return
     */
    @Override
    public List<ApplicantsDO> preadmissionLimit(Map<String, Object> map) {
        return dao.preadmissionlistlimit(map);
    }

    /**
     * 查询所有拟录取人员；并按分数排列
     * @param map
     * @return
     */
    @Override
    public List<ApplicantsDO> preadmissionAll(Map<String, Object> map) {
        return dao.preadmissionlist(map);
    }

    @Override
    public List<ApplicantsDO> listType() {
        return dao.listType();
    }

    @Override
    public int updateScore(ApplicantsDO entity) {
        return dao.updateScore(entity);
    }

    @Override
    public List<ApplicantsDO> getExpotList(Map<String, Object> mapSql) {
        return dao.getExportList(mapSql);
    }

    @Override
    public List<ApplicantsDO> exportlist(Map<String, Object> m) {
        return dao.exportlist(m);
    }

    @Override
    public List<ApplicantsDO> getExcellents(String userid) {
        return dao.getExcellents(userid);
    }

    @Override
    public List<ApplicantsDO> getAppicants(String jobid, String userid) {
        return dao.getAppicants(jobid, userid);
    }

    @Override
    public int countPreAdmission(Query query) {
        return dao.countPreAdmission(query);
    }
}
