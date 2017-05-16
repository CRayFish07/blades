package com.iusofts.blades.sys.web.permission;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解-标记权限名称
 * @author：Ivan
 * @date： 2015年1月10日 下午16:02:22
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.METHOD,ElementType.TYPE})  
@Inherited 
public @interface Permission {
	
	/**
	 * 权限名称
	 * @return
	 */
	public String value()default "";
	
	/**
	 * 是否需要检查
	 * @return
	 */
	public boolean check()default true;
	
}
