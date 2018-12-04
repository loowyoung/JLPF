package com.anxin.applicants.domain.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.anxin.common.domain.BaseEntity;
import java.util.Date;
import java.util.Objects;


/**
 * 候选人信息表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-16 18:42:41
 */
@ExcelTarget("Applicants")
public class Applicants extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//候选人姓名
	@Excel(name = "姓名",orderNum = "1",width = 8)
	private String username;
	//性别
	@Excel(name = "性别", replace = {"男_1", "女_2"}, orderNum = "2",width = 5)
	private String sex;
	//应聘岗位
	@Excel(name = "应聘岗位",orderNum = "3",width = 22)
	private String job;
	//出生年月
	@Excel(name = "出生年月", exportFormat = "yyyy/MM/dd", orderNum = "4",width = 10)
	private Date birthday;
	//身份证号
	@Excel(name = "身份证号",orderNum = "5",width = 19)
	private String idnumber;
	//手机号
	@Excel(name = "手机",orderNum = "14",width = 12)
	private String phone;
	//邮箱
	@Excel(name = "邮件地址",orderNum = "15",width = 21)
	private String mail;
	//政治面貌
	@Excel(name = "政治面貌",orderNum = "16",width = 8.5)
	private String politics;
	//学历
	@Excel(name = "学历",orderNum = "17",width = 8.5)
	private String education;
	//是否优才
//	@Excel(name = "是否优才",orderNum = "6",width = 9)
	private String type;
	//本科学校
	@Excel(name = "本科",orderNum = "9",width = 17)
	private String bachelorschool;
	//硕士学校
	@Excel(name = "硕士",orderNum = "8",width = 17)
	private String masterschool;
	//博士学校
	@Excel(name = "博士",orderNum = "7",width = 17)
	private String doctorschool;
	//本科专业
	private String bachelormajor;
	//硕士专业
	private String mastermajor;
	//博士专业
	private String doctormajor;
	//毕业时间
	@Excel(name = "毕业时间",exportFormat = "yyyy/MM/dd",orderNum = "10",width = 10)
	private Date graduationdate;
	//生源地
	@Excel(name = "生源地",orderNum = "11",width = 9)
	private String hometown;
	//外语等级
	@Excel(name = "外语等级(分数)",orderNum = "12",width = 20)
	private String languagelevel;
	//计算机等级
	@Excel(name = "计算机等级",orderNum = "18",width = 13)
	private String computerlevel;
	//简历识别码
	@Excel(name = "简历识别码",orderNum = "27",width = 36)
	private String resumenum;
	//简历地址
	@Excel(name = "简历地址",orderNum = "26",width = 90)
	private String resumeurl;
	//面试成绩
	@Excel(name = "面试成绩",orderNum = "21",width = 9)
	private Double interviewresult;
	//英语分数
	@Excel(name = "英语成绩",orderNum = "20",width = 9)
	private String englishscore;
	//状态
	@Excel(name = "状态",orderNum = "13",width = 8)
	private String status;
	//面试场次
	@Excel(name = "面试场次",orderNum = "22",width = 20)
	private String interviewdate;
	//岗位一
	@Excel(name = "岗位一",orderNum = "23",width = 44)
	private String jobone;
	//岗位二
	@Excel(name = "岗位二",orderNum = "24",width = 44)
	private String jobtwo;
	//岗位三
	@Excel(name = "岗位三",orderNum = "25",width = 44)
	private String jobthree;
	//免试原因
	@Excel(name = "免试原因",orderNum = "18",width = 20)
	private String exemptionreason;
	//备注
	@Excel(name = "备注",orderNum = "28",width = 14)
	private String remarks;

	//面试总分
	@Excel(name = "面试总分",orderNum = "29",width = 10)
	private String sumscore;
	//评委人数
	@Excel(name = "评委人数",orderNum = "30",width = 10)
	private String counttempuser;

	//行变色标记
	private String flag;

	public Applicants() {

	}


	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 设置：候选人姓名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：候选人姓名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 设置：应聘岗位
	 */
	public void setJob(String job) {
		this.job = job;
	}
	/**
	 * 获取：应聘岗位
	 */
	public String getJob() {
		return job;
	}
	/**
	 * 设置：出生年月
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * 获取：出生年月
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * 设置：身份证号
	 */
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	/**
	 * 获取：身份证号
	 */
	public String getIdnumber() {
		return idnumber;
	}
	/**
	 * 设置：手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机号
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：邮箱
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * 获取：邮箱
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * 设置：政治面貌
	 */
	public void setPolitics(String politics) {
		this.politics = politics;
	}
	/**
	 * 获取：政治面貌
	 */
	public String getPolitics() {
		return politics;
	}
	/**
	 * 设置：人员类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：人员类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：本科学校
	 */
	public void setBachelorschool(String bachelorschool) {
		this.bachelorschool = bachelorschool;
	}
	/**
	 * 获取：本科学校
	 */
	public String getBachelorschool() {
		return bachelorschool;
	}
	/**
	 * 设置：硕士学校
	 */
	public void setMasterschool(String masterschool) {
		this.masterschool = masterschool;
	}
	/**
	 * 获取：硕士学校
	 */
	public String getMasterschool() {
		return masterschool;
	}
	/**
	 * 设置：博士学校
	 */
	public void setDoctorschool(String doctorschool) {
		this.doctorschool = doctorschool;
	}
	/**
	 * 获取：博士学校
	 */
	public String getDoctorschool() {
		return doctorschool;
	}
	/**
	 * 设置：本科专业
	 */
	public void setBachelormajor(String bachelormajor) {
		this.bachelormajor = bachelormajor;
	}
	/**
	 * 获取：本科专业
	 */
	public String getBachelormajor() {
		return bachelormajor;
	}
	/**
	 * 设置：硕士专业
	 */
	public void setMastermajor(String mastermajor) {
		this.mastermajor = mastermajor;
	}
	/**
	 * 获取：硕士专业
	 */
	public String getMastermajor() {
		return mastermajor;
	}
	/**
	 * 设置：博士专业
	 */
	public void setDoctormajor(String doctormajor) {
		this.doctormajor = doctormajor;
	}
	/**
	 * 获取：博士专业
	 */
	public String getDoctormajor() {
		return doctormajor;
	}
	/**
	 * 设置：毕业时间
	 */
	public void setGraduationdate(Date graduationdate) {
		this.graduationdate = graduationdate;
	}
	/**
	 * 获取：毕业时间
	 */
	public Date getGraduationdate() {
		return graduationdate;
	}
	/**
	 * 设置：生源地
	 */
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	/**
	 * 获取：生源地
	 */
	public String getHometown() {
		return hometown;
	}
	/**
	 * 设置：外语等级
	 */
	public void setLanguagelevel(String languagelevel) {
		this.languagelevel = languagelevel;
	}
	/**
	 * 获取：外语等级
	 */
	public String getLanguagelevel() {
		return languagelevel;
	}
	/**
	 * 设置：计算机等级
	 */
	public void setComputerlevel(String computerlevel) {
		this.computerlevel = computerlevel;
	}
	/**
	 * 获取：计算机等级
	 */
	public String getComputerlevel() {
		return computerlevel;
	}
	/**
	 * 设置：简历识别码
	 */
	public void setResumenum(String resumenum) {
		this.resumenum = resumenum;
	}
	/**
	 * 获取：简历识别码
	 */
	public String getResumenum() {
		return resumenum;
	}
	/**
	 * 设置：简历地址
	 */
	public void setResumeurl(String resumeurl) {
		this.resumeurl = resumeurl;
	}
	/**
	 * 获取：简历地址
	 */
	public String getResumeurl() {
		return resumeurl;
	}
	/**
	 * 设置：面试成绩
	 */
	public void setInterviewresult(Double interviewresult) {
		this.interviewresult = interviewresult;
	}
	/**
	 * 获取：面试成绩
	 */
	public Double getInterviewresult() {
		return interviewresult;
	}
	/**
	 * 设置：英语分数
	 */
	public void setEnglishscore(String englishscore) {
		this.englishscore = englishscore;
	}
	/**
	 * 获取：英语分数
	 */
	public String getEnglishscore() {
		return englishscore;
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
	/**
	 * 设置：面试场次
	 */
	public void setInterviewdate(String interviewdate) {
		this.interviewdate = interviewdate;
	}
	/**
	 * 获取：面试场次
	 */
	public String getInterviewdate() {
		return interviewdate;
	}
	/**
	 * 设置：岗位一
	 */
	public void setJobone(String jobone) {
		this.jobone = jobone;
	}
	/**
	 * 获取：岗位一
	 */
	public String getJobone() {
		return jobone;
	}
	/**
	 * 设置：岗位二
	 */
	public void setJobtwo(String jobtwo) {
		this.jobtwo = jobtwo;
	}
	/**
	 * 获取：岗位二
	 */
	public String getJobtwo() {
		return jobtwo;
	}
	/**
	 * 设置：岗位三
	 */
	public void setJobthree(String jobthree) {
		this.jobthree = jobthree;
	}
	/**
	 * 获取：岗位三
	 */
	public String getJobthree() {
		return jobthree;
	}
	/**
	 * 设置：免试原因
	 */
	public void setExemptionreason(String exemptionreason) {
		this.exemptionreason = exemptionreason;
	}
	/**
	 * 获取：免试原因
	 */
	public String getExemptionreason() {
		return exemptionreason;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSumscore() {
		return sumscore;
	}

	public void setSumscore(String sumscore) {
		this.sumscore = sumscore;
	}

	public String getCounttempuser() {
		return counttempuser;
	}

	public void setCounttempuser(String counttempuser) {
		this.counttempuser = counttempuser;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}


	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Applicants)){
			return false;
		}
		Applicants a =(Applicants)obj;
		return  Objects.equals(username, a.username) &&
				Objects.equals(sex, a.sex)&&
				Objects.equals(job, a.job)&&
				Objects.equals(idnumber, a.idnumber)&&
				Objects.equals(phone, a.phone)&&
				Objects.equals(resumeurl, a.resumeurl)&&
				Objects.equals(interviewresult, null);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, sex, job,idnumber,phone,resumeurl,interviewresult);
	}

}
