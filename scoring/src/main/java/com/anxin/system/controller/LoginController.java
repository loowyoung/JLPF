package com.anxin.system.controller;

import com.anxin.common.annotation.Log;
import com.anxin.common.controller.BaseController;
import com.anxin.common.domain.FileDO;
import com.anxin.common.domain.Tree;
import com.anxin.common.service.FileService;
import com.anxin.common.utils.MD5Utils;
import com.anxin.common.utils.Query;
import com.anxin.common.utils.R;
import com.anxin.common.utils.ShiroUtils;
import com.anxin.system.domain.MenuDO;
import com.anxin.system.domain.UserDO;
import com.anxin.system.service.MenuService;
import com.anxin.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.aspectj.weaver.ast.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MenuService menuService;
	@Autowired
	FileService fileService;
	@Autowired
	UserService userService;
	@GetMapping({ "/", "" })
	String welcome(Model model) {

		return "redirect:/index";
	}

	@Log("请求访问主页")
	@GetMapping({ "/index" })
	String index(Model model) {
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		FileDO fileDO = fileService.get(getUser().getPicId());
		if(fileDO!=null&&fileDO.getUrl()!=null){
			if(fileService.isExist(fileDO.getUrl())){
				model.addAttribute("picUrl",fileDO.getUrl());
			}else {
				model.addAttribute("picUrl","/img/photo_s.png");
			}
		}else {
			model.addAttribute("picUrl","/img/photo_s.png");
		}
		model.addAttribute("username", getUser().getUsername());
		return "index_v1";
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password) {

		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return R.ok();
		} catch (AuthenticationException e) {
			return R.error("密码错误");
		}
	}

	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

	@GetMapping("/changePassword")
	String changePassword() {
		return "changePassword";
	}

	/**
	 * 重置Admin的密码为123456
	 * @param uname
	 * @param email
	 * @return
	 */
	@PostMapping("/sendPassword")
	@ResponseBody
	R sendPassword(String uname,String email) {
		return userService.resetPassword(uname,email);
	}

	@PostMapping("/changeEmail")
	@ResponseBody
	R changeEmail(String username,String password,String newemail) {
		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return userService.resetAdmin(username,newemail);
		} catch (AuthenticationException e) {
			return R.error("密码错误");
		}
	}

}
