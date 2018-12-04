package com.anxin.tempuser.domain;

import com.anxin.common.domain.BaseEntity;
import springfox.documentation.service.ApiListing;

import java.util.List;


/**
 * 面试官临时账户表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-16 19:53:36
 */
public class TempuserDO extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	//账号
	private String username;
	//密码
	private String password;
	//状态
	private String status;
	//岗位名称
	private String jobname;
	//临时用户岗位关联表
	private List<String> jobsid;
	//批量生成用户个数
	private String count;
	//批量修改的用户id组
	private String[] ids;
	//用户自增数字
	private long usernum;
	//搜索框开始时间，用来查某一时间段的候选人
	private String searchstarttime;
	//搜索框结束时间
	private String searchendtime;

	/**
	 * 设置：账号
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：账号
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public String getStatus() {
		return status;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public List<String> getJobsid() {
		return jobsid;
	}

	public void setJobsid(List<String> jobsid) {
		this.jobsid = jobsid;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public long getUsernum() {
		return usernum;
	}

	public void setUsernum(long usernum) {
		this.usernum = usernum;
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
}
