package com.anxin.operationlog.controller;


import java.util.List;
import java.util.Map;


import com.anxin.common.controller.BaseController;
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

import com.anxin.operationlog.domain.OperationlogDO;
import com.anxin.operationlog.service.OperationlogService;
import com.anxin.common.utils.PageUtils;
import com.anxin.common.utils.Query;
import com.anxin.common.utils.R;

import javax.servlet.http.HttpServletResponse;

import static com.anxin.common.utils.EasyPoiUtil.exportExcel;

/**
 * 临时账户操作日志表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-17 10:39:48
 */
 
@Controller
@RequestMapping("/operationlog/operationlog")
public class OperationlogController extends BaseController {
	@Autowired
	private OperationlogService operationlogService;
	
	@GetMapping()
	@RequiresPermissions("operationlog:operationlog:operationlog")
	String Operationlog(){
	    return "operationlog/operationlog/operationlog";
	}


	@RequestMapping("exportLogList")
	public void export(HttpServletResponse response){
		List<OperationlogDO> list = operationlogService.list(null);
		exportExcel(list,"操作日志","操作日志",OperationlogDO.class,"操作日志.xls",true,response);
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("operationlog:operationlog:operationlog")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<OperationlogDO> operationlogList = operationlogService.list(query);
		int total = operationlogService.count(query);
		PageUtils pageUtils = new PageUtils(operationlogList, total);
		return pageUtils;
	}

	/**
	 * 下拉框查询所有临时用户
	 * @return
	 */
	@GetMapping("/listUser")
	@ResponseBody
	public List<OperationlogDO> listJob() {
		return operationlogService.listUser();
	};
	
	@GetMapping("/add")
	@RequiresPermissions("operationlog:operationlog:add")
	String add(){
	    return "operationlog/operationlog/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("operationlog:operationlog:edit")
	String edit(@PathVariable("id") String id,Model model){
		OperationlogDO operationlog = operationlogService.get(id);
		model.addAttribute("operationlog", operationlog);
	    return "operationlog/operationlog/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("operationlog:operationlog:add")
	public R save( OperationlogDO operationlog){
		if(operationlogService.save(operationlog)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("operationlog:operationlog:edit")
	public R update( OperationlogDO operationlog){
		operationlogService.update(operationlog);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("operationlog:operationlog:remove")
	public R remove( String id){
		if(operationlogService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("operationlog:operationlog:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		operationlogService.batchRemove(ids);
		return R.ok();
	}
	
}
