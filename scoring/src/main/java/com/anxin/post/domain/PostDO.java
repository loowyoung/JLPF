package com.anxin.post.domain;

import com.anxin.common.domain.BaseEntity;


/**
 * 岗位表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-16 19:35:42
 */
public class PostDO extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//岗位名称
	private String jobname;
	//岗位状态
	private String status;
	//岗位名额
	private String quota;
	//递补人数
	private String candidate;
	//描述
	private String description;
	//多选框是否选中
	private boolean checked;
	//搜索框开始时间，用来查某一时间段的候选人
	private String searchstarttime;
	//搜索框结束时间
	private String searchendtime;

	/**
	 * 设置：id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：岗位名称
	 */
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	/**
	 * 获取：岗位名称
	 */
	public String getJobname() {
		return jobname;
	}
	/**
	 * 设置：岗位状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：岗位状态
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：岗位名额
	 */
	public void setQuota(String quota) {
		this.quota = quota;
	}
	/**
	 * 获取：岗位名额
	 */
	public String getQuota() {
		return quota;
	}


	/**
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getSearchstarttime() {
		return searchstarttime;
	}

	public void setSearchstarttime(String searchstarttime) {
		this.searchstarttime = searchstarttime;
	}

	public String getSearchendtime() {
		return searchendtime;
	}

	public void setSearchendtime(String searchendtime) {
		this.searchendtime = searchendtime;
	}

	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}
}
