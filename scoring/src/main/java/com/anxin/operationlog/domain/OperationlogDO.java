package com.anxin.operationlog.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.anxin.common.domain.BaseEntity;

import java.util.Date;



/**
 * 临时账户操作日志表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-17 10:39:48
 */
@ExcelTarget("Operationlog")
public class OperationlogDO extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//临时账户
	@Excel(name = "用户名",orderNum = "1",width = 10)
	private String tempuserId;
	//候选人
	private String applicantId;
	//操作
	@Excel(name = "操作信息",orderNum = "2",width = 50)
	private String operate;
	//时间
	@Excel(name = "操作时间",orderNum = "3", exportFormat = "yyyy-MM-dd HH:mm:ss",width = 20)
	private Date date;
	//搜索框开始时间，用来查某一时间段的候选人
	private String searchstarttime;
	//搜索框结束时间
	private String searchendtime;


	/**
	 * 设置：id
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	@Override
	public String getId() {
		return id;
	}
	/**
	 * 设置：临时账户
	 */
	public void setTempuserId(String tempuserId) {
		this.tempuserId = tempuserId;
	}
	/**
	 * 获取：临时账户
	 */
	public String getTempuserId() {
		return tempuserId;
	}
	/**
	 * 设置：候选人
	 */
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	/**
	 * 获取：候选人
	 */
	public String getApplicantId() {
		return applicantId;
	}
	/**
	 * 设置：操作
	 */
	public void setOperate(String operate) {
		this.operate = operate;
	}
	/**
	 * 获取：操作
	 */
	public String getOperate() {
		return operate;
	}
	/**
	 * 设置：时间
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * 获取：时间
	 */
	public Date getDate() {
		return date;
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
