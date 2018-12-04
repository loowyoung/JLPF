package com.anxin.plan.domain;

import com.anxin.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;



/**
 * 面试场次表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-17 08:38:39
 */
public class PlanDO extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//用户姓名
	private String username;
	//性别
	private String sex;
	//身份证号
	private String idnumber;
	//手机号
	private String phone;
	//岗位
	private String jobname;
	//面试官
	private String tempuser;
	//未打分评委
	private String notscore;
	//开始时间
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date starttime;
	//结束时间
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endtime;

	/**
	 * 设置：开始时间
	 */
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getStarttime() {
		return starttime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getEndtime() {
		return endtime;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getUsername() {
		return username;
	}

	public String getSex() {
		return sex;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public String getPhone() {
		return phone;
	}

	public String getTempuser() {
		return tempuser;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setTempuser(String tempuser) {
		this.tempuser = tempuser;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getNotscore() {
		return notscore;
	}

	public void setNotscore(String notscore) {
		this.notscore = notscore;
	}
}
