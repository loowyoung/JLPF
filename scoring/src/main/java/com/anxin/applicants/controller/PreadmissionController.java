package com.anxin.applicants.controller;

import java.util.List;
import java.util.Map;

import com.anxin.applicants.domain.ApplicantsDO;
import com.anxin.applicants.service.ApplicantsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anxin.common.utils.PageUtils;
import com.anxin.common.utils.Query;
import com.anxin.common.utils.R;

/**
 * 候选人信息表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-17 11:15:46
 */
 
@Controller
@RequestMapping("/preadmission/applicants")
public class PreadmissionController {
	@Autowired
	private ApplicantsService applicantsService;
	
	@GetMapping()
	@RequiresPermissions("preadmission:applicants:applicants")
	String Applicants(){
	    return "preadmission/applicants/applicants";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("preadmission:applicants:applicants")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询拟录取候选人，其中包括面试总分和评委人数
        Query query = new Query(params);
		List<ApplicantsDO> applicantsList = applicantsService.preadmissionLimit(query);
		int total = applicantsService.countPreAdmission(query);
		PageUtils pageUtils = new PageUtils(applicantsList, total);
		return pageUtils;
	}

	/**
	 * 下拉框查询所有岗位
	 * @return
	 */
	@GetMapping("/type")
	@ResponseBody
	public List<ApplicantsDO> listType() {
		return applicantsService.listType();
	};

    /**
     * 页面加载时，重设候选人录取类型
     * @return
     */
    @GetMapping("/setAdmissionType")
    @ResponseBody
    public R setAdmissionType() {
        applicantsService.setAdmissionType();
        return R.ok();
    };
	
	@GetMapping("/add")
	@RequiresPermissions("preadmission:applicants:add")
	String add(){
	    return "preadmission/applicants/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("preadmission:applicants:edit")
	String edit(@PathVariable("id") String id,Model model){
		ApplicantsDO applicants = applicantsService.get(id);
		model.addAttribute("applicants", applicants);
	    return "preadmission/applicants/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("preadmission:applicants:add")
	public R save( ApplicantsDO applicants){
		if(applicantsService.save(applicants)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("preadmission:applicants:edit")
	public R update( ApplicantsDO applicants){
		applicantsService.update(applicants);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("preadmission:applicants:remove")
	public R remove( String id){
		if(applicantsService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("preadmission:applicants:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		applicantsService.batchRemove(ids);
		return R.ok();
	}
	
}
