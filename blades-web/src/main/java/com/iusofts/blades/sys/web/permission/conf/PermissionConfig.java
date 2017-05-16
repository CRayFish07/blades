package com.iusofts.blades.sys.web.permission.conf;

import java.util.Map;

import com.iusofts.blades.sys.web.util.Dom4jUtil;


/**
 * 锦衣卫配置信息
 * @author：Ivan
 * @date： 2015年9月2日 上午10:44:17
 */
public class PermissionConfig {
	public static String PROJECT_NAME="";
	public static String PROJECT_PACKAGE_NAME="";
	public static String CONTROLLER_PACKAGE_NAME="";
	public static String[] NOT_CHECK_URI=null;
	public static boolean IS_SHOW_NOTCHECK=false;
	public static Map<String,String> DEFAULT_PACKAGE_NAME=null;
	static{
		flush();
	}
	public static void flush(){
		System.out.println("-----刷新配置-----");
		Map<String,String> defaultConfig= Dom4jUtil.getMapById("/config/permission-config.xml","bladesDefaultConfig");
		PROJECT_NAME=defaultConfig.get("projectName");
		PROJECT_PACKAGE_NAME=defaultConfig.get("projectPackageName");
		CONTROLLER_PACKAGE_NAME=defaultConfig.get("controllerPackageName");
		NOT_CHECK_URI=defaultConfig.get("notCheckUri").trim().replace(" ", "").split("\n");
		DEFAULT_PACKAGE_NAME=Dom4jUtil.getMapById("/config/permission-config.xml","bladesDefaultPackageName");
		IS_SHOW_NOTCHECK=Boolean.parseBoolean(defaultConfig.get("showNotCheckUri"));
	}
	public static void main(String[] args) {
		System.out.println(PROJECT_NAME);
		System.out.println(PROJECT_PACKAGE_NAME);
		System.out.println(IS_SHOW_NOTCHECK);
	}
}
