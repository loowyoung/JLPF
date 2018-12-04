package com.anxin.score.dao;

import com.anxin.applicants.domain.model.AvScore;
import com.anxin.common.dao.BaseDao;
import com.anxin.score.domain.ScoreDO;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 评委候选人打分表
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-21 13:21:34
 */
@Mapper
public interface ScoreDao extends BaseDao<ScoreDO>{

    ScoreDO getByUseridAndApplicantid(Map<String, Object> m);

    AvScore getAvScore(String applicantId);
}
