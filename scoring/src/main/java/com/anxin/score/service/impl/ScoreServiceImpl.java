package com.anxin.score.service.impl;

import com.anxin.applicants.domain.model.AvScore;
import com.anxin.common.service.impl.BaseImpl;
import org.springframework.stereotype.Service;

import com.anxin.score.dao.ScoreDao;
import com.anxin.score.domain.ScoreDO;
import com.anxin.score.service.ScoreService;

import java.util.Map;


@Service
public class ScoreServiceImpl extends BaseImpl<ScoreDao,ScoreDO> implements ScoreService {

    @Override
    public ScoreDO getByUseridAndApplicantid(Map<String, Object> m) {
        return dao.getByUseridAndApplicantid(m);
    }

    @Override
    public AvScore getAvScore(String applicantId) {
        return dao.getAvScore(applicantId);
    }
}
