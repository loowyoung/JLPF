package com.anxin.interview.controller;


import com.anxin.applicants.domain.ApplicantsDO;
import com.anxin.applicants.domain.model.Applicants;
import com.anxin.applicants.domain.model.AvScore;
import com.anxin.applicants.service.ApplicantsService;
import com.anxin.common.utils.Query;
import com.anxin.common.utils.R;
import com.anxin.common.utils.StringUtils;
import com.anxin.common.utils.ValidateHelper;
import com.anxin.operationlog.domain.OperationlogDO;
import com.anxin.operationlog.service.OperationlogService;
import com.anxin.post.domain.PostDO;
import com.anxin.post.service.PostService;
import com.anxin.score.domain.ScoreDO;
import com.anxin.score.domain.vo.Score;
import com.anxin.score.service.ScoreService;
import com.anxin.tempuser.domain.TempuserDO;
import com.anxin.tempuser.service.TempuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 前端面试接口
 * Created by sun on 2018/10/22.
 */
@Controller
@RequestMapping("/ax")
public class HfController {


    @Autowired
    private TempuserService tempuserService;
    @Autowired
    private PostService postService;
    @Autowired
    private ApplicantsService applicantsService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private OperationlogService operationlogService;

    public static final String FLAG = "0";

    /**
     * 登录
     *
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public R login(@RequestParam Map<String, Object> map) {
        if (StringUtils.isBlank(map.get("username").toString())
                || StringUtils.isBlank(map.get("password").toString())) {
            return R.error(1, "参数值为空或参数错误");
        }
        //查询列表数据
        List<TempuserDO> tempuserList = tempuserService.getByname(map);
        if (ValidateHelper.isNotEmptyCollection(tempuserList)) {
            TempuserDO tempuser = tempuserList.get(0);
            if (tempuser != null) {
                String password = map.get("password").toString();
                if (password.equals(tempuser.getPassword())) {
                    if ("0".equals(tempuser.getStatus())) {
//                        SaveUserLog(map.get("username").toString(), "", "登录系统失败，账户处于停用状态");
                        return R.error(4003, "此账户已停用");
                    }
                    tempuser.setPassword(null);
//                    SaveUserLog(map.get("username").toString(), "", "登录系统成功");
                    return R.ok("success").put("data", tempuser);
                } else {
//                    SaveUserLog(map.get("username").toString(), "", "登录系统失败，用户名或密码错误");
                    return R.error(4002, "用户名或密码错误");
                }
            }
        }
        return R.error(4001, "用户不存在");
    }

    /**
     * 保存用户操作日志
     *
     * @param TempuserId  用户名
     * @param ApplicantId 候选人名
     * @param Operate     操作
     */
    private void SaveUserLog(String TempuserId, String ApplicantId, String Operate) {
        OperationlogDO o = new OperationlogDO();
        o.setTempuserId(TempuserId);
        o.setApplicantId(ApplicantId);
        o.setOperate(Operate);
        o.setDate(new Date());
        operationlogService.save(o);
    }

    /**
     * 获取用户的岗位列表信息
     *
     * @param userid   用户id
     * @param username 用户名称
     * @return
     */
    @ResponseBody
    @GetMapping("/getJobs")
    public R getJobs(@RequestParam String userid, @RequestParam String username) {
        if (StringUtils.isBlank(userid) || StringUtils.isBlank(username)) {
            return R.error(1, "参数值为空或参数错误");
        }
        List<PostDO> jobs = postService.getJobs(userid);
        if (ValidateHelper.isEmptyCollection(jobs)) {
//            SaveUserLog(username,"","查看岗位列表信息为空");
            return R.error(4001, "岗位信息为空");
        }
//        SaveUserLog(username, "", "查看岗位列表信息");
        return R.ok("success").put("data", jobs);
    }

    /**
     * 获取优才人员列表
     *
     * @param userid   用户id
     * @param username 用户名称
     * @return
     */
    @ResponseBody
    @GetMapping("/getExcellents")
    public R getExcellents(@RequestParam String userid, @RequestParam String username, Integer pageIndex, Integer pageSize) {
        if (StringUtils.isBlank(userid) || StringUtils.isBlank(username)) {
            return R.error(1, "参数值为空或参数错误");
        }
        List<ApplicantsDO> apps = applicantsService.getExcellents(userid);
        if (ValidateHelper.isEmptyCollection(apps)) {
//            SaveUserLog(username,"","查看优才人员列表为空");
            return R.error(4001, "无优才人员信息");
        }
        //判断是否面试过标记
        SetMsFlag(apps);


//        apps.forEach(app -> {
//            if (app.getInterviewresult() != null) {
//                app.setMsflag("1");
//            } else {
//                app.setMsflag("0");
//            }
//        });
//        SaveUserLog(username, "", "查看优才人员列表");
//        List<ApplicantsDO> sublist = Pagination(pageIndex, pageSize, apps);
        return R.ok(apps.size()).put("data", apps);
    }

    /**
     * 判断是否面试过标记
     *
     * @param apps
     */
    private void SetMsFlag(List<ApplicantsDO> apps) {
        apps.forEach(app -> {
            if (FLAG.equals(app.getFlag())) {
                ApplicantsDO ado = applicantsService.getByNameAndJob(app.getUsername(), app.getJob());
                if (ado != null) {
                    if (ado.getInterviewresult() != null) {
                        app.setMsflag("1");
                    } else {
                        app.setMsflag("0");
                    }
                }
            }
        });
    }

    private List<ApplicantsDO> Pagination(Integer pageIndex, Integer pageSize, List<ApplicantsDO> list) {
        int size = list.size();
        int pageCount = size / pageSize;
        int fromIndex = pageSize * (pageIndex - 1);
        int toIndex = fromIndex + pageSize;
        if (toIndex >= size) {
            toIndex = size;
        }
        if (pageIndex > pageCount + 1) {
            fromIndex = 0;
            toIndex = 0;
        }
        return list.subList(fromIndex, toIndex);
    }


    /**
     * 获取岗位人员列表
     *
     * @param jobid    岗位id
     * @param userid   用户id
     * @param username 用户名称
     * @return
     */
    @ResponseBody
    @GetMapping("/getAppicants")
    public R getAppicants(@RequestParam String jobid, @RequestParam String userid, @RequestParam String username, Integer pageIndex, Integer pageSize) {
        if (StringUtils.isBlank(jobid) || StringUtils.isBlank(userid) || StringUtils.isBlank(username)) {
            return R.error(1, "参数值为空或参数错误");
        }
        List<ApplicantsDO> apps = applicantsService.getAppicants(jobid, userid);
        if (ValidateHelper.isEmptyCollection(apps)) {
            return R.error(4001, "无人员信息");
        }
        SetMsFlag(apps);
//        SaveUserLog(username, "", "查看" + postService.getJobInfo(jobid).getJobname() + "人员列表");
//        List<ApplicantsDO> sublist = Pagination(pageIndex, pageSize, apps);
        return R.ok(apps.size()).put("data", apps);
    }

    /**
     * 获取岗位详情
     *
     * @param jobid 岗位id
     * @return
     */
    @ResponseBody
    @GetMapping("/getJobInfo")
    public R getJobInfo(@RequestParam String jobid) {
        if (StringUtils.isBlank(jobid)) {
            return R.error(1, "参数值为空或参数错误");
        }
        PostDO job = postService.getJobInfo(jobid);
        return R.ok("success").put("data", job);
    }

//    /**
//     * 保存排名信息
//     * @param rank 名次
//     * @param userid 用户id
//     * @param applicantid 候选人id
//     * @return
//     */
//    @ResponseBody
//    @PostMapping("/saveRank")
//    public R saveRank(@RequestParam String userid,@RequestParam String applicantid,@RequestParam String rank){
//        if (StringUtils.isBlank(userid)||StringUtils.isBlank(applicantid)||StringUtils.isBlank(rank)) {
//            return R.error(1,"参数值为空或参数错误");
//        }
//        TempuserDO tempuserDO = tempuserService.get(userid);
//        Map<String,Object> m= new HashMap<>(16);
//        m.put("userid",userid);
//        m.put("applicantid",applicantid);
//        ApplicantsDO app = applicantsService.get(applicantid);
//        ScoreDO core= scoreService.getByUseridAndApplicantid(m);
//        if (core!=null) {
//            //更新
//            core.setRanking(rank);
//            core.setUpdateBy(tempuserDO.getUsername());
//            scoreService.update(core);
//            if (app!=null&&app.getUsername()!=null) {
//                SaveUserLog(tempuserDO.getUsername(),app.getUsername(),"给 "+app.getUsername()+" 的排名由"+core.getRanking()+"修改为 "+rank);
//            }
//            return R.ok();
//        }else {
//            //保存
//            ScoreDO score =new ScoreDO();
//            score.setTempuserId(userid);
//            score.setApplicantId(applicantid);
//            score.setRanking(rank);
//            score.setCreateBy(tempuserDO.getUsername());
//            score.setUpdateBy(tempuserDO.getUsername());
//            int c= scoreService.save(score);
//            if (c>0) {
//                SaveUserLog(tempuserDO.getUsername(),app.getUsername(),"给 "+app.getUsername()+" 添加排名为 "+rank);
//                return R.ok();
//            }
//        }
//        return R.error();
//    }


    /**
     * 保存排名信息
     * scorelist 分数排名列表
     * userid 用户id
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/saveRank")
    public R saveRank(@RequestBody Score scorevo) {
        if (scorevo == null) {
            return R.error(1, "参数值为空或参数错误");
        }
        List<ScoreDO> scorelist = scorevo.getScorelist();
        String userid = scorevo.getUserid();
        if (ValidateHelper.isEmptyCollection(scorelist) || StringUtils.isBlank(userid)) {
            return R.error(1, "列表为空或参数不正确");
        }

        long count = scorelist.stream().filter(a -> a.getApplicantId() == null || a.getTempuserId() == null).count();
        if (count > 0) {
            return R.error(1, "列表参数属性为空");
        }

        TempuserDO tempuserDO = tempuserService.get(userid);
        Vector<Thread> threads = new Vector<>();
        scorelist.forEach(a -> {
            Thread t = new Thread(() -> {
                Map<String, Object> m = new HashMap<>(16);
                m.put("userid", a.getTempuserId());
                m.put("applicantid", a.getApplicantId());
                ScoreDO core = scoreService.getByUseridAndApplicantid(m);
                ApplicantsDO app = applicantsService.get(a.getApplicantId());
                if (core != null) {
                    //更新
                    //如果参数值排名为空
                    rankingIsNull(tempuserDO, a, core, app);
                } else {
                    //保存
                    ScoreDO score = new ScoreDO();
                    score.setTempuserId(userid);
                    if (StringUtils.isNotBlank(a.getRanking())) {
                        score.setRanking(a.getRanking());
                        if (app != null && app.getUsername() != null) {
                            score.setApplicantId(a.getApplicantId());
                            score.setCreateBy(tempuserDO.getUsername());
                            score.setUpdateBy(tempuserDO.getUsername());
                            scoreService.save(score);
                            SaveAvScore(a);
                            SaveUserLog(tempuserDO.getUsername(), app.getUsername(), "给 " + app.getUsername() + " 添加排名为 " + a.getRanking());
                        }
                    }

                }
            });
            threads.add(t);
            t.start();
        });
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return R.ok("提交成功");
    }

    /**
     * 面试结果（查询候选人面试成绩，总分，评委总数）
     *
     * @param map
     * @return
     */
    @ResponseBody
    @GetMapping("/listAvg")
    public R listAvg(@RequestParam Map<String, Object> map, Integer pageIndex, Integer pageSize) {
        if (StringUtils.isBlank(map.get("job") + "")) {
            return R.error(1, "参数为空或参数错误");
        }
        List<ApplicantsDO> applicantsList = applicantsService.getInterviewResult(map);
        if (ValidateHelper.isEmptyCollection(applicantsList)) {
            return R.error(4001, "无人员信息");
        }
        return R.ok("success").put("data", applicantsList);
    }

    /**
     * 如果参数值排名为空
     *
     * @param tempuserDO
     * @param a
     * @param core
     * @param app
     */
    private void rankingIsNull(TempuserDO tempuserDO, ScoreDO a, ScoreDO core, ApplicantsDO app) {
        if (StringUtils.isBlank(a.getRanking())) {
            String oldRanking = null;
            if (StringUtils.isNotBlank(core.getRanking())) {
                oldRanking = core.getRanking();
            }
            core.setRanking("");
            if (app != null && app.getUsername() != null) {
                core.setUpdateBy(tempuserDO.getUsername());
                int updateResult = scoreService.update(core);
                if (updateResult > 0) {
                    if (oldRanking != null) {
                        SaveUserLog(tempuserDO.getUsername(), app.getUsername(), "给 " + app.getUsername() + " 的排名由" + oldRanking + "修改为空");
                    }

                }
            }
        } else {
            //如果参数值排名不为空
            if (!a.getRanking().equals(core.getRanking())) {
                String oldRanking = core.getRanking();
                core.setRanking(a.getRanking());
                if (app != null && app.getUsername() != null) {
                    core.setUpdateBy(tempuserDO.getUsername());
                    int updateResult = scoreService.update(core);
                    if (updateResult > 0) {
                        SaveUserLog(tempuserDO.getUsername(), app.getUsername(), "给 " + app.getUsername() + " 的排名由" + ("".equals(oldRanking.trim()) ? "空" : oldRanking) + "修改为 " + a.getRanking());
                    }
                }
            }
        }
    }


    /**
     * 保存分数信息
     * scorelist 分数排名列表
     * userid 用户id
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/saveScore")
    public R saveScore(@RequestBody Score scorevo) {
        if (scorevo == null) {
            return R.error(1, "参数值为空或参数错误");
        }
        List<ScoreDO> scorelist = scorevo.getScorelist();
        String userid = scorevo.getUserid();
        if (ValidateHelper.isEmptyCollection(scorelist) || StringUtils.isBlank(userid)) {
            return R.error(1, "列表为空或参数不正确");
        }

        long count = scorelist.stream().filter(a -> a.getApplicantId() == null || a.getTempuserId() == null).count();
        if (count > 0) {
            return R.error(1, "列表参数属性为空");
        }

        long dfcount = scorelist.stream().filter(a -> StringUtils.isBlank(a.getScore())).count();
        if (dfcount > 0) {
            return R.error(1, "请输入全部得分后提交");
        }

        TempuserDO tempuserDO = tempuserService.get(userid);
        scorelist.forEach(a -> {
            Map<String, Object> m = new HashMap<>(16);
            m.put("userid", a.getTempuserId());
            m.put("applicantid", a.getApplicantId());
            ScoreDO core = scoreService.getByUseridAndApplicantid(m);
            ApplicantsDO app = applicantsService.get(a.getApplicantId());
            if (core != null) {
                //更新
                UpdateScore(tempuserDO, a, core, app);
            } else {
                //保存
                SaveScore(userid, tempuserDO, a, app);
            }

        });
        return R.ok("提交成功");
    }

    /**
     * 保存分数
     *
     * @param userid
     * @param tempuserDO
     * @param a
     * @param app
     */
    private void SaveScore(String userid, TempuserDO tempuserDO, ScoreDO a, ApplicantsDO app) {
        ScoreDO score = new ScoreDO();
        score.setTempuserId(userid);
        if (StringUtils.isNotBlank(a.getScore() + "")) {
            score.setScore(a.getScore());
            if (app != null && app.getUsername() != null) {
                score.setApplicantId(a.getApplicantId());
                score.setCreateBy(tempuserDO.getUsername());
                score.setUpdateBy(tempuserDO.getUsername());
                int saveResult = scoreService.save(score);
                if (saveResult > 0) {
                    SaveAvScore(a);
//                    SaveUserLog(tempuserDO.getUsername(), app.getUsername(), "给 " + app.getUsername() + " 打分： " + a.getScore() + " 分");
                }
            }
        }
    }

    /**
     * 更新分数
     *
     * @param tempuserDO
     * @param a
     * @param core
     * @param app
     */
    private void UpdateScore(TempuserDO tempuserDO, ScoreDO a, ScoreDO core, ApplicantsDO app) {
        if (StringUtils.isNotBlank(a.getScore())) {
            //判断传来的分数和数据库分数比较
//            String oldScore = null;
//            if (!a.getScore().equals(core.getScore())) {
//                if (StringUtils.isNotBlank(core.getScore())) {
//                    oldScore = core.getScore();
//                }
            core.setScore(a.getScore());
            if (app != null && app.getUsername() != null) {
                core.setUpdateBy(tempuserDO.getUsername());
                int updateResult = scoreService.update(core);
                if (updateResult > 0) {
                    SaveAvScore(a);
//                        if (StringUtils.isNotBlank(oldScore)) {
//                            SaveUserLog(tempuserDO.getUsername(), app.getUsername(), "给 " + app.getUsername() + " 打分由" + oldScore + "修改为 " + a.getScore());
//                        } else {
//                            SaveUserLog(tempuserDO.getUsername(), app.getUsername(), "给 " + app.getUsername() + " 打分： " + a.getScore() + " 分");
//                        }

                }
            }
//            }
        }
    }


    /**
     * 保存平均分
     *
     * @param a
     */
    private void SaveAvScore(ScoreDO a) {
        AvScore as = scoreService.getAvScore(a.getApplicantId());
        if (as != null) {
            if (StringUtils.isNotBlank(as.getAvfs())) {
                ApplicantsDO aDo = new ApplicantsDO();
                aDo.setId(a.getApplicantId());
                String r = new DecimalFormat("#.00").format(Double.parseDouble(as.getAvfs()));
                aDo.setInterviewresult(Double.parseDouble(r));
                applicantsService.update(aDo);

            }
        }
    }

    /**
     * 保存单个分数
     *
     * @param userid      面试官id
     * @param applicantid 候选人id
     * @param score       分数
     * @return
     */
    @ResponseBody
    @PostMapping("/submitScore")
    public R saveOneScores(@RequestParam("userid") String userid, @RequestParam("applicantid") String applicantid, @RequestParam("score") String score) {
        if (StringUtils.isBlank(userid) || StringUtils.isBlank(applicantid) || StringUtils.isBlank(score)) {
            return R.error(1, "参数值为空或参数错误");
        }
        TempuserDO tempuserDO = tempuserService.get(userid);
        Map<String, Object> m = new HashMap<>(16);
        m.put("userid", userid);
        m.put("applicantid", applicantid);
        ScoreDO core = scoreService.getByUseridAndApplicantid(m);
        ApplicantsDO app = applicantsService.get(applicantid);
        if (core != null) {
            //更新分数
            updateOneScore(score, tempuserDO, core, app);
        } else {
            //保存分数
            saveOneScore(userid, applicantid, score, tempuserDO, app);
        }
        return R.ok("success").put("data", "提交成功");
    }

    /**
     * 更新单个分数
     *
     * @param score
     * @param tempuserDO
     * @param core
     * @param app
     */
    private void updateOneScore(@RequestParam String score, TempuserDO tempuserDO, ScoreDO core, ApplicantsDO app) {
        //判断传来的分数和数据库分数比较
        String oldScore = null;
        if (!score.equals(core.getScore())) {
            if (StringUtils.isNotBlank(core.getScore())) {
                oldScore = core.getScore();
            }
            core.setScore(score);
            if (app != null && app.getUsername() != null) {
                core.setUpdateBy(tempuserDO.getUsername());
                int updateResult = scoreService.update(core);
                if (updateResult > 0) {
                    if (StringUtils.isNotBlank(oldScore)) {
                        SaveUserLog(tempuserDO.getUsername(), app.getUsername(), "给 " + app.getUsername() + " 打分由" + oldScore + "修改为 " + score);
                    } else {
                        SaveUserLog(tempuserDO.getUsername(), app.getUsername(), "给 " + app.getUsername() + " 打分： " + score + " 分");
                    }

                }
            }
        }
    }

    /**
     * 保存单个分数
     *
     * @param userid
     * @param applicantid
     * @param score
     * @param tempuserDO
     * @param app
     */
    private void saveOneScore(@RequestParam String userid, @RequestParam String applicantid, @RequestParam String score, TempuserDO tempuserDO, ApplicantsDO app) {
        ScoreDO scoreDO = new ScoreDO();
        scoreDO.setTempuserId(userid);
        scoreDO.setScore(score);
        if (app != null && app.getUsername() != null) {
            scoreDO.setApplicantId(applicantid);
            scoreDO.setCreateBy(tempuserDO.getUsername());
            scoreDO.setUpdateBy(tempuserDO.getUsername());
            int saveResult = scoreService.save(scoreDO);
            if (saveResult > 0) {
                SaveUserLog(tempuserDO.getUsername(), app.getUsername(), "给 " + app.getUsername() + " 打分： " + score + " 分");
            }
        }
    }

}
