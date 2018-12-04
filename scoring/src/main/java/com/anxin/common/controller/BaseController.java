package com.anxin.common.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import com.anxin.common.utils.ShiroUtils;
import com.anxin.system.domain.UserDO;

@Controller
public class BaseController {

	/**
	 * 日志对象
	 */
	public Logger logger = LoggerFactory.getLogger(getClass());

	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}