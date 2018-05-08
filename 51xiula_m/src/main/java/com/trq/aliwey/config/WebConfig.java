package com.trq.aliwey.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jranuan.config.Constants;
import com.jranuan.config.Handlers;
import com.jranuan.config.Interceptors;
import com.jranuan.config.JRanuanConfig;
import com.jranuan.config.Plugins;
import com.jranuan.config.Routes;
import com.jranuan.ext.handler.SessionHandler;
import com.jranuan.ext.kit.ConfigPropKit;
import com.jranuan.ext.plugin.config.ConfigPlugin;
import com.jranuan.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jranuan.ext.plugin.tablebind.SimpleNameStyles;
import com.jranuan.ext.route.AutoBindRoutes;
import com.jranuan.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jranuan.plugin.druid.DruidPlugin;
import com.jranuan.plugin.ehcache.EhCachePlugin;
import com.trq.aliwey.controller.BaseController;
import com.trq.aliwey.controller.IndexController;
import com.trq.aliwey.interceptor.LoginInterceptor;
import com.trq.aliwey.interceptor.RouteInterceptor;

/**
 * API引导式配置
 */
public class WebConfig extends JRanuanConfig {
	
	public static String rootPath = "";
	
	/**
	 * 配置常量
	 */
	@Override
	public void configConstant(Constants me) {
		me.setError403View("/WEB-INF/pages/err403.html");
		me.setError404View("/WEB-INF/pages/err404.html");
		me.setError500View("/WEB-INF/pages/err500.html");
		me.setBaseViewPath("/WEB-INF/pages/");
		me.setDevMode(ConfigPropKit.getToBool("devMode", true));
		me.setEncoding("utf-8");
	}
	
	/**
	 * 配置路由
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void configRoute(Routes me) {
		// 新增自动路由
		AutoBindRoutes abr = new AutoBindRoutes();
		abr.autoScan(true);
		abr.addExcludeClasses(BaseController.class);
		me.add(abr);
		me.add("/", IndexController.class);
	}
	
	/**
	 * 配置插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 新增 阿里巴巴druid数据库连接池插件
		DruidPlugin trqDruid = new DruidPlugin(ConfigPropKit.get("jdbc.url"), ConfigPropKit.get("jdbc.username"), ConfigPropKit.get("jdbc.password"), ConfigPropKit.get("jdbc.driverClassName"));
		trqDruid.setInitialSize(2);
		trqDruid.addFilter(new StatFilter());
		// WallFilter的功能是防御SQL注入攻击
		WallFilter wallDefault = new WallFilter();
		wallDefault.setDbType("mysql");
		trqDruid.addFilter(wallDefault);
		trqDruid.setLogAbandoned(true);
		trqDruid.setRemoveAbandoned(true);
		me.add(trqDruid);

		// 新增 自动绑定model与表关系插件
		AutoTableBindPlugin def = new AutoTableBindPlugin(trqDruid, SimpleNameStyles.LOWER);
		def.setShowSql(ConfigPropKit.getToBool("jdbc.showsql", true));
		def.setContainerFactory(new CaseInsensitiveContainerFactory());
		def.autoScan(false);
		me.add(def);
		
		// 新增 ehcache缓存插件
		me.add(new EhCachePlugin());
		// 新增 配置文件插件
		me.add(new ConfigPlugin());
	}
	
	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		// 路由拦截器
		me.add(new RouteInterceptor());
		// 登录拦截器
		me.add(new LoginInterceptor());
		
	}
	
	/**
	 * 配置处理器
	 */
	@Override
	public void configHandler(Handlers me) {
		me.add(new SessionHandler());// 将session里的参数传递到request中直接得到
	}
}
