package com.iusofts.blades.sys.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统全局信息设置
 * 
 * @author Ivan
 * @date 2016年2月16日 下午4:30:25
 */
public class AppListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		// 上下文路径
		context.setAttribute("ctx", context.getContextPath());
		// 系统代码
		context.setAttribute("appCode", context.getInitParameter("appCode"));
		// 系统名称
		context.setAttribute("appName", context.getInitParameter("appName"));
		// 版权单位
		context.setAttribute("copyright", context.getInitParameter("copyright"));
		// 技术支持
		context.setAttribute("support", context.getInitParameter("support"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
}
