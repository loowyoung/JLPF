package com.anxin.plan.controller;

import java.util.List;
import java.util.Map;

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

import com.anxin.plan.domain.PlanDO;
import com.anxin.plan.service.PlanService;
import com.anxin.common.utils.PageUtils;
import com.anxin.common.utils.Query;
import com.anxin.common.utils.R;

/**
 * 面试场次表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-17 08:38:39
 */
 
@Controller
@RequestMapping("/plan/plan")
public class PlanController {
	@Autowired
	private PlanService planService;
	
	@GetMapping()
	@RequiresPermissions("plan:plan:plan")
	String Plan(){
	    return "plan/plan/plan";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("plan:plan:plan")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PlanDO> planList = planService.list(query);
		int total = planService.count(query);
		PageUtils pageUtils = new PageUtils(planList, total);
		return pageUtils;
	}

	/**
	 * 下拉框查询所有岗位
	 * @return
	 */
	@GetMapping("/listJob")
	@ResponseBody
	public List<PlanDO> listJob() {
		return planService.listJob();
	};

	@GetMapping("/add")
	@RequiresPermissions("plan:plan:add")
	String add(){
	    return "plan/plan/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("plan:plan:edit")
	String edit(@PathVariable("id") String id,Model model){
		PlanDO plan = planService.get(id);
		model.addAttribute("plan", plan);
	    return "plan/plan/edit";
	}

	/**
	 * 删除
	 */
	@PostMapping( "/batchSetTime")
	@ResponseBody
	@RequiresPermissions("plan:plan:edit")
	public R editTime(@RequestParam("ids[]") String[] ids,@RequestParam("starttime") String starttime,@RequestParam("endtime") String endtime){
		planService.batchSetTime(ids,starttime,endtime);
		return R.ok();
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("plan:plan:add")
	public R save( PlanDO plan){
		if(planService.save(plan)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("plan:plan:edit")
	public R update( PlanDO plan){
		planService.update(plan);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("plan:plan:remove")
	public R remove( String id){
		if(planService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("plan:plan:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		planService.batchRemove(ids);
		return R.ok();
	}
	
}
