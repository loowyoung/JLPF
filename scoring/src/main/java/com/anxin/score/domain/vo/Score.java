package com.anxin.score.domain.vo;

import com.anxin.score.domain.ScoreDO;

import java.util.List;

/**
 * Created by sun on 2018/10/23.
 */
public class Score {
    List<ScoreDO> scorelist;
    //用户id
    String userid;

    public List<ScoreDO> getScorelist() {
        return scorelist;
    }

    public void setScorelist(List<ScoreDO> scorelist) {
        this.scorelist = scorelist;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
