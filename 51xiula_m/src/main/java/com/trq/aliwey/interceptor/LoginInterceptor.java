package com.trq.aliwey.interceptor;

import java.io.IOException;

import com.jranuan.aop.Interceptor;
import com.jranuan.core.ActionInvocation;
import com.trq.aliwey.config.WebConfig;
import com.trq.aliwey.controller.BaseController;

/**
 * 
 * <pre>
 * [概 要] LoginInterceptor定义
 * 
 * [详 细] 用户登录拦截器
 * 
 * [备 考] 无
 * 
 * [环 境] JavaSE 1.6.37
 * 
 * @author tan.p
 * @date 2015-2-28 下午04:15:43
 * @version 1.0
 * @see
 * </pre>
 */
public class LoginInterceptor implements Interceptor {
	/*
	 * (无-Javadoc)
	 * 
	 * @see
	 * com.jranuan.aop.Interceptor#intercept(com.jranuan.core.ActionInvocation)
	 */
	public void intercept(ActionInvocation ai) {
		BaseController c = (BaseController) ai.getController();
		Object obj = c.getSessionAttr(BaseController.LoginSession);
		if (obj == null) {
			try {
				c.getResponse().setCharacterEncoding("GB2312");
				c.getResponse().getWriter().print("<script>top.location.href='" + WebConfig.rootPath + "/web/login/login/1';</script>");
			} catch (IOException e) {
				c.redirect("/web/login/login");
			}
		} else {
			ai.invoke();
		}
	}
}