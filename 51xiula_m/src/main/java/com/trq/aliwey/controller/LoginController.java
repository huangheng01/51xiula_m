package com.trq.aliwey.controller;


import com.jranuan.aop.Clear;
import com.jranuan.core.ActionKey;
import com.jranuan.ext.route.ControllerBind;
import com.trq.aliwey.controller.BaseController;
import com.trq.aliwey.interceptor.LoginInterceptor;

/**
 * 
 * <pre>
 * [概 要] LoginController 定义
 * 
 * [详 细] 登录控制器
 * 
 * [备 考] 无
 * 
 * [环 境] JavaSE 1.7
 * 
 */
@Clear({ LoginInterceptor.class })
@ControllerBind(path = "login")
public class LoginController extends BaseController {

	// 登录页面
	@ActionKey("login")
	public void index() {

		this.display();
	}
	
}
