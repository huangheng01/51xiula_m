package com.trq.aliwey.controller;

import com.jranuan.aop.Clear;
import com.jranuan.core.ActionKey;
import com.trq.aliwey.controller.BaseController;
import com.trq.aliwey.interceptor.LoginInterceptor;



@Clear({ LoginInterceptor.class })
public class IndexController extends BaseController {

	@ActionKey("/")
	public void index() {

		this.display();
	}
	
}
