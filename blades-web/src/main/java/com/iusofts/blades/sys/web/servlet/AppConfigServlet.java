package com.iusofts.blades.sys.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 应用配置信息
 * 1.获取方式:一是从配置文件中获取,二是从数据库中获取;
 * 2.优先级:数据库中的配置信息优先于配置文件.
 * @author Ivan
 * @date 2016年2月16日 下午4:37:52
 */
public class AppConfigServlet extends HttpServlet {

	private static final long serialVersionUID = -6303267985344240570L;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//重置配置信息
		super.doGet(req, resp);
	}

}
