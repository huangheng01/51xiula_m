package com.trq.aliwey.interceptor;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import com.jranuan.aop.Interceptor;
import com.jranuan.core.ActionInvocation;
import com.jranuan.core.Controller;
import com.jranuan.ext.kit.ConfigPropKit;
import com.jranuan.ext.kit.TimeKit;
import com.jranuan.kit.StrKit;
import com.trq.aliwey.config.WebConfig;
import com.trq.aliwey.controller.BaseController;

/** 
 * 
 * <pre>
 * [概 要] RouteInterceptor定义
 * 
 * [详 细] 路由拦截器
 * 
 * [备 考] 无
 * 
 * [环 境] JavaSE 1.7
 * 
 * @author tan.p
 * @date 2015年12月25日下午5:56:39
 * @version 1.0
 * </pre>
 */
public class RouteInterceptor implements Interceptor {

	private Map<String, Integer> redisTemplate = new HashMap<String, Integer>();
	/* (无-Javadoc)
	 * @see com.jranuan.aop.Interceptor#intercept(com.jranuan.core.ActionInvocation)
	 */
	public void intercept(ActionInvocation ai) {
		Controller c = ai.getController();
		c.renderNull();
		String ck = ai.getControllerKey();
		int lastIndex = ck.lastIndexOf("/");
		//String g = ck.substring(1, lastIndex);
		String m = ck.substring(lastIndex+1);
		String a = ai.getMethodName();
		Boolean isGet = c.getRequest().getMethod().equals("GET");
		Boolean isPost = !isGet;
		String requestType = c.getRequest().getHeader("X-Requested-With");
		Boolean isAjax = (requestType!=null && requestType.equals("XMLHttpRequest"));
		if(WebConfig.rootPath.equals("")) {
			WebConfig.rootPath = c.getRequest().getScheme() + "://" + c.getRequest().getHeader("host") +  c.getRequest().getContextPath();
		}
		
		c.setAttr("root", ConfigPropKit.get("basePath"));
		//c.setAttr("GROUP_NAME", g);
		c.setAttr("MODULE_NAME", m);
		c.setAttr("ACTION_NAME", a);
		c.setAttr("VIEW_NAME", (a));
		c.setAttr("IS_GET", isGet);
		c.setAttr("IS_POST", isPost);
		c.setAttr("IS_AJAX", isAjax);
		c.setAttr("NOW_DATE", TimeKit.getCurrentDate());
		c.setAttr("NOW_YEAR", Integer.valueOf(TimeKit.getYear()));
		c.setAttr("NOW_MONTH", Integer.valueOf(TimeKit.getMonth()));
		c.setAttr("NOW_DATETIME", TimeKit.getCurrentTime());
		c.setAttr("PART", c.getPara());
		
		// 系统参数平台名称
		c.setAttr("SITE_NAME", ConfigPropKit.get("SITE_NAME", "51秀啦"));
		c.setAttr("COPYRIGHT", ConfigPropKit.get("copyright", "CopyRight © 2018-"+TimeKit.getYear()+" By aliwey"));
		c.setAttr("SUPPORT", "技术部");
		
		//String ip=getClientIpAddress(c.getRequest());
		  
		

		// 工具类支持
		c.setAttr("StrKit", StrKit.class);
		ai.invoke();
	}
	/* public void urlHandle(HttpServletRequest request, long limitTime,int limitCount) throws Exception {
	        try {
	            String ip = getClientIpAddress(request);
	            String url = request.getRequestURL().toString();
	            final String key = "req_limit_".concat(url).concat(ip);

	            if(redisTemplate.get(key)==null || redisTemplate.get(key)==0){
	                redisTemplate.put(key,1);
	            }else{
	                redisTemplate.put(key,redisTemplate.get(key)+1);
	            }
	            int count = redisTemplate.get(key);
	            if (count > 0) {
	                Timer timer= new Timer();
	                TimerTask task  = new TimerTask(){
	                    @Override
	                    public void run() {
	                        redisTemplate.remove(key);
	                    }
	                };
	                timer.schedule(task, limitTime);
	            }
	            if (count > limitCount){
	                Calendar calendar = Calendar.getInstance();
	                Date iptime=calendar.getTime();
	                TrqIpBlack black=new TrqIpBlack();
	                black.set("ip", ip);
	                black.set("iptime", iptime);
	                black.save();
	                throw new Exception();
	            }
	        } catch (Exception e) {
	            throw e;
	        }
	    }*/
		 public static String getClientIpAddress(HttpServletRequest request){
		     String ip = request.getHeader("X-Forwarded-For");
		     if (ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
		         ip = request.getHeader("Proxy-Client-IP");
		     }
		     if (ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
		         ip = request.getHeader("WL-Proxy-Client-IP");
		     }
		     if (ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
		         ip = request.getHeader("HTTP_CLIENT_IP");
		     }if (ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
		         ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		     }
		     if (ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
		         ip = request.getRemoteAddr();
		     }
		     return ip;
		 }
}
