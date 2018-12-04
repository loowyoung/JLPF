package com.anxin.tempuser.service.impl;

import com.anxin.common.service.impl.BaseImpl;
import com.anxin.common.utils.StringUtils;
import com.anxin.post.dao.PostDao;
import com.anxin.post.domain.PostDO;
import com.anxin.tempuser.domain.JobTempuserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anxin.tempuser.dao.TempuserDao;
import com.anxin.tempuser.domain.TempuserDO;
import com.anxin.tempuser.service.TempuserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class TempuserServiceImpl extends BaseImpl<TempuserDao,TempuserDO> implements TempuserService {

    @Autowired
    private PostDao postDao;

    /**
     * 保存临时用户，并保存临时用户所属的岗位
     * @param tempuser
     * @return
     */
    @Transactional
    @Override
    public int save(TempuserDO tempuser) {
        int count = -1;
        if (StringUtils.isBlank(tempuser.getId())) {
            tempuser.preInsert();
            count = dao.save(tempuser);//保存临时用户
        }
        String tempuserid = tempuser.getId();
        List<String> jobsid = tempuser.getJobsid();
        dao.removeJobs(tempuserid);
        List<JobTempuserDO> list = new ArrayList<>();
        for(String jobid : jobsid){
            JobTempuserDO jobTempuserDO = new JobTempuserDO();
            if (StringUtils.isBlank(jobTempuserDO.getId())) {
                jobTempuserDO.preInsert();
            }
            jobTempuserDO.setTempuserid(tempuserid);
            jobTempuserDO.setJobid(jobid);
            list.add(jobTempuserDO);//临时用户和岗位多对多对应
        }
        if(list.size()>0){
            dao.saveJobTempuser(list);//保存历临时用户和岗位对应信息
        }
        return count;
    }

    /**
     * 根据临时用户id，设置岗位选中状态
     * @param tempuserid
     * @return
     */
    @Override
    public List<PostDO> list(String tempuserid) {
        List<JobTempuserDO> jobTempuserList = dao.listJobTempByid(tempuserid);
        List<PostDO> postList = postDao.list(null);
        for(PostDO post:postList){
            post.setChecked(false);
            for(JobTempuserDO jobTempuser : jobTempuserList){
                if(Objects.equals(jobTempuser.getJobid(),post.getId())){
                    post.setChecked(true);
                }
            }
        };
        return postList;
    }

    /**
     * 修改临时用户，和关联岗位
     * @param user
     * @return
     */
    @Transactional
    @Override
    public int update(TempuserDO user) {
        int count = dao.update(user);
        String tempuserid = user.getId();
        List<String> jobsid = user.getJobsid();
        if(null != jobsid) {
            dao.removeJobs(tempuserid);
            List<JobTempuserDO> list = new ArrayList<>();
            for(String jobid : jobsid){
                JobTempuserDO jobTempuserDO = new JobTempuserDO();
                if (StringUtils.isBlank(jobTempuserDO.getId())) {
                    jobTempuserDO.preInsert();
                }
                jobTempuserDO.setTempuserid(tempuserid);
                jobTempuserDO.setJobid(jobid);
                list.add(jobTempuserDO);//临时用户和岗位多对多对应
            }
            if(list.size()>0){
                dao.saveJobTempuser(list);//保存历临时用户和岗位对应信息
            }
        }
        return count;
    }

    /**
     * 批量修改临时用户的密码和岗位
     * @param tempuserDO
     * @return
     */
    @Transactional
    public int batchUpdate(TempuserDO tempuserDO){
        int count = -1;
        if((null!=tempuserDO.getPassword())&&(!"".equals(tempuserDO.getPassword()))){
            for(int i=0;i<tempuserDO.getIds().length;i++){
                TempuserDO tempuser = new TempuserDO();
                tempuser.setId(tempuserDO.getIds()[i]);
                tempuser.setPassword(tempuserDO.getPassword());
                tempuser.setRemarks(tempuserDO.getRemarks());
                dao.update(tempuser);
            }
        }
        if(null!=tempuserDO.getJobsid()){
            for(int i=0;i<tempuserDO.getIds().length;i++){
                dao.removeJobs(tempuserDO.getIds()[i]);
                List<JobTempuserDO> list = new ArrayList<>();
                for(String jobid : tempuserDO.getJobsid()){
                    JobTempuserDO jobTempuserDO = new JobTempuserDO();
                    if (StringUtils.isBlank(jobTempuserDO.getId())) {
                        jobTempuserDO.preInsert();
                    }
                    jobTempuserDO.setTempuserid(tempuserDO.getIds()[i]);
                    jobTempuserDO.setJobid(jobid);
                    list.add(jobTempuserDO);//临时用户和岗位多对多对应
                }
                if(list.size()>0){
                    count = dao.saveJobTempuser(list);//保存临时用户和岗位对应信息
                }
            }
        }
        return count;
    }

    @Override
    public List<Integer> maxUserNum() {
        return dao.maxUserNum();
    }

    @Override
    public List<TempuserDO> getByname(Map<String, Object> map) {
        return dao.getByname(map);
    }
}
