package com.anxin.score.service;

import com.anxin.applicants.domain.model.AvScore;
import com.anxin.common.service.BaseService;
import com.anxin.score.domain.ScoreDO;

import java.util.Map;

/**
 * 评委候选人打分表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-21 13:21:34
 */
public interface ScoreService extends BaseService<ScoreDO>{

    ScoreDO getByUseridAndApplicantid(Map<String, Object> m);

    AvScore getAvScore(String applicantId);
}
