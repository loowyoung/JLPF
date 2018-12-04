package com.anxin.score.domain;

import com.anxin.common.domain.BaseEntity;

import java.util.Date;



/**
 * 评委候选人打分表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-21 13:21:34
 */
public class ScoreDO extends BaseEntity{
	private static final long serialVersionUID = 1L;

	//临时账户id
	private String tempuserId;
	//候选人id
	private String applicantId;
	//分数
	private String score;
	//打分时间
	private Date scoretime;
	//名次
	private String ranking;

	/**
	 * 设置：临时账户id
	 */
	public void setTempuserId(String tempuserId) {
		this.tempuserId = tempuserId;
	}
	/**
	 * 获取：临时账户id
	 */
	public String getTempuserId() {
		return tempuserId;
	}
	/**
	 * 设置：候选人id
	 */
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	/**
	 * 获取：候选人id
	 */
	public String getApplicantId() {
		return applicantId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * 设置：打分时间
	 */
	public void setScoretime(Date scoretime) {
		this.scoretime = scoretime;
	}
	/**
	 * 获取：打分时间
	 */
	public Date getScoretime() {
		return scoretime;
	}
	/**
	 * 设置：名次
	 */
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	/**
	 * 获取：名次
	 */
	public String getRanking() {
		return ranking;
	}

}
