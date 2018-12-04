package com.anxin.score.controller;

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

import com.anxin.score.domain.ScoreDO;
import com.anxin.score.service.ScoreService;
import com.anxin.common.utils.PageUtils;
import com.anxin.common.utils.Query;
import com.anxin.common.utils.R;

/**
 * 评委候选人打分表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-21 13:21:34
 */
 
@Controller
@RequestMapping("/score/score")
public class ScoreController {
	@Autowired
	private ScoreService scoreService;
	
	@GetMapping()
	@RequiresPermissions("score:score:score")
	String Score(){
	    return "score/score/score";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("score:score:score")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ScoreDO> scoreList = scoreService.list(query);
		int total = scoreService.count(query);
		PageUtils pageUtils = new PageUtils(scoreList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("score:score:add")
	String add(){
	    return "score/score/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("score:score:edit")
	String edit(@PathVariable("id") String id,Model model){
		ScoreDO score = scoreService.get(id);
		model.addAttribute("score", score);
	    return "score/score/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("score:score:add")
	public R save( ScoreDO score){
		if(scoreService.save(score)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("score:score:edit")
	public R update( ScoreDO score){
		scoreService.update(score);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("score:score:remove")
	public R remove( String id){
		if(scoreService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("score:score:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		scoreService.batchRemove(ids);
		return R.ok();
	}
	
}
