package com.iusofts.blades.sys.common.util;

import java.util.Collection;

/**
 * 
 * @描述 验证工具类
 * @author Asker_lve
 * @date 2016年3月10日 下午2:11:02
 */
public class ValidateUtil {

	/**
	 * 验证集合有效性
	 */
	public static boolean isValid(Collection<?> col) {
		return (col == null || col.isEmpty()) == true ? false : true;
	}

	/**
	 * 验证字符串数组有效性
	 */
	public static boolean isValid(String[] values) {
		return (values == null || values.length == 0) == true ? false : true;
	}

	/**
	 * 验证数组有效性
	 */
	public static boolean isValid(Object[] arr) {
		if (arr == null || arr.length == 0) {
			return false;
		}
		return true;
	}
}
