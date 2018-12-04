package com.anxin.post.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anxin.post.domain.PostDO;
import com.anxin.post.service.PostService;
import com.anxin.common.utils.PageUtils;
import com.anxin.common.utils.Query;
import com.anxin.common.utils.R;

/**
 * 岗位表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-16 19:35:42
 */
 
@Controller
@RequestMapping("/post/post")
public class PostController {
	@Autowired
	private PostService postService;
	
	@GetMapping()
	@RequiresPermissions("post:post:post")
	String Post(){
	    return "post/post/post";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("post:post:post")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PostDO> postList = postService.list(query);
		int total = postService.count(query);
		PageUtils pageUtils = new PageUtils(postList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("post:post:add")
	String add(){
	    return "post/post/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("post:post:edit")
	String edit(@PathVariable("id") String id,Model model){
		PostDO post = postService.get(id);
		model.addAttribute("post", post);
	    return "post/post/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("post:post:add")
	public R save( PostDO post){
//		Map<String,Object> query = new HashMap<>();
//		query.put("status","1");
		List<PostDO> postList = postService.list(null);
		for(int i=0;i<postList.size();i++){
			if (postList.get(i).getJobname().equals(post.getJobname().trim())) {
				return R.error("岗位已存在");
			}
		}
		if(postService.save(post)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("post:post:edit")
	public R update( PostDO post){
		postService.update(post);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("post:post:remove")
	public R remove( String id){
		if(postService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("post:post:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		postService.batchRemove(ids);
		return R.ok();
	}
	/**
	 * 修改岗位状态
	 */
	@ResponseBody
	@RequestMapping("/changeStauts")
	@RequiresPermissions("post:post:edit")
	public R changeStauts(String id,String status){
		PostDO postDo = new PostDO();
		postDo.setId(id);
		postDo.setStatus(status);
		postService.update(postDo);
		return R.ok();
	}
}
