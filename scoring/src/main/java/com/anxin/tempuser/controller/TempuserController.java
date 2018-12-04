package com.anxin.tempuser.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anxin.common.utils.*;
import com.anxin.post.domain.PostDO;
import com.anxin.post.service.PostService;
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

import com.anxin.tempuser.domain.TempuserDO;
import com.anxin.tempuser.service.TempuserService;

/**
 * 面试官临时账户表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-16 19:53:36
 */
 
@Controller
@RequestMapping("/tempuser/tempuser")
public class TempuserController {
	@Autowired
	private TempuserService tempuserService;
	@Autowired
	private PostService postService;

	@GetMapping()
	@RequiresPermissions("tempuser:tempuser:tempuser")
	String Tempuser(){
	    return "tempuser/tempuser/tempuser";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("tempuser:tempuser:tempuser")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TempuserDO> tempuserList = tempuserService.list(query);
		int total = tempuserService.count(query);
		PageUtils pageUtils = new PageUtils(tempuserList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("tempuser:tempuser:add")
	String add(Model model){
		Map<String,Object> param = new HashMap();
		param.put("status","1");
		List<PostDO> jobs = postService.list(param);
		model.addAttribute("jobs",jobs);
	    return "tempuser/tempuser/add";
	}

	@GetMapping("/batchAdd")
	@RequiresPermissions("tempuser:tempuser:add")
	String batchAdd(Model model){
		Map<String,Object> param = new HashMap();
		param.put("status","1");
		List<PostDO> jobs = postService.list(param);
		model.addAttribute("jobs",jobs);
		return "tempuser/tempuser/batchAdd";
	}

	@GetMapping("/batchEdit")
	@RequiresPermissions("tempuser:tempuser:edit")
	String batchEdit(Model model){
		Map<String,Object> param = new HashMap();
		param.put("status","1");
		List<PostDO> jobs = postService.list(param);
		model.addAttribute("jobs",jobs);
		return "tempuser/tempuser/batchEdit";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("tempuser:tempuser:edit")
	String edit(@PathVariable("id") String id,Model model){
		TempuserDO tempuser = tempuserService.get(id);
		model.addAttribute("tempuser", tempuser);
		List<PostDO> jobs = tempuserService.list(id);
		for(int i=0;i<jobs.size();i++){
			PostDO postDO = jobs.get(i);
			if(null!=postDO&&"0".equals(postDO.getStatus())){
				jobs.remove(postDO);
			}
		}
		model.addAttribute("jobs",jobs);
	    return "tempuser/tempuser/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("tempuser:tempuser:add")
	public R save( TempuserDO tempuser){
	    if(ValidateHelper.isEmptyCollection(tempuser.getJobsid())){
	        return R.error("请选择岗位");
        }
        if(StringUtils.isBlank(tempuser.getPassword())){
            return R.error("请输入密码");
        }
		List<Integer> userNumList = tempuserService.maxUserNum();
		int maxUserNum=1;
	    for(int i=0;i<userNumList.size()+1;i++){
	    	if(!userNumList.contains(i+1)){
	    		maxUserNum = i+1;
	    		break;
			}
		}
		String username;
		if(maxUserNum<10){
			username = "user00"+maxUserNum;
		}else if(maxUserNum>=10&&maxUserNum<100){
			username = "user0"+maxUserNum;
		}else {
			username = "user"+maxUserNum;
		}
		tempuser.setUsername(username);
		tempuser.setUsernum(maxUserNum);
		if(tempuserService.save(tempuser)>0){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 批量保存
	 */
	@ResponseBody
	@PostMapping("/batchSave")
	@RequiresPermissions("tempuser:tempuser:add")
	public R batchSave(TempuserDO tempuser){
		if(ValidateHelper.isEmptyCollection(tempuser.getJobsid())){
			return R.error("请选择岗位");
		}
		if(StringUtils.isBlank(tempuser.getPassword())){
			return R.error("请输入密码");
		}
		if(StringUtils.isBlank(tempuser.getCount()+"")){
			return R.error("请输入用户数量");
		}
		int r = -1;
		for(int i=0;i<Integer.parseInt(tempuser.getCount());i++){
			TempuserDO tempuserDo = new TempuserDO();
			tempuserDo.setPassword(tempuser.getPassword());
			tempuserDo.setJobsid(tempuser.getJobsid());
			List<Integer> userNumList = tempuserService.maxUserNum();
			int maxUserNum=1;
			for(int j=0;i<userNumList.size()+1;j++){
				if(!userNumList.contains(j+1)){
					maxUserNum = j+1;
					break;
				}
			}
			String username;
			if(maxUserNum<10){
				username = "user00"+maxUserNum;
			}else if(maxUserNum>=10&&maxUserNum<99){
				username = "user0"+maxUserNum;
			}else {
				username = "user"+maxUserNum;
			}
			if("".equals(tempuserDo.getStatus())||null==tempuserDo.getStatus()){
				tempuserDo.setStatus("1");
				tempuserDo.setRemarks("");
			}
			tempuserDo.setUsername(username);
			tempuserDo.setUsernum(maxUserNum);
			r = tempuserService.save(tempuserDo);

		}
		if(r>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tempuser:tempuser:edit")
	public R update( TempuserDO tempuser){
		tempuserService.update(tempuser);
		return R.ok();
	}

	/**
	 * 批量修改
	 */
	@ResponseBody
	@RequestMapping("/batchUpdate")
	@RequiresPermissions("tempuser:tempuser:edit")
	public R batchUpdate( TempuserDO tempuser){
		tempuserService.batchUpdate(tempuser);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("tempuser:tempuser:remove")
	public R remove( String id){
		if(tempuserService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("tempuser:tempuser:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		tempuserService.batchRemove(ids);
		return R.ok();
	}

	/**
	 * 修改临时用户状态
	 */
	@ResponseBody
	@RequestMapping("/changeStauts")
	@RequiresPermissions("tempuser:tempuser:edit")
	public R changeStauts(String id,String status){
		TempuserDO tempuserDO = new TempuserDO();
		tempuserDO.setId(id);
		tempuserDO.setStatus(status);
		tempuserService.update(tempuserDO);
		return R.ok();
	}
	
}
