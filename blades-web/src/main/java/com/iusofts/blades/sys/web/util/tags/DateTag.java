package com.iusofts.blades.sys.web.util.tags;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.iusofts.blades.sys.common.util.date.DateUtil;
import com.iusofts.blades.sys.web.util.ConvertUtil;
import org.apache.commons.lang.StringUtils;


/**
 * 
 * @ClassName: 标签处理器 (DateTag.java)
 * 
 * @Description:
 * 
 * @Date: 2013-7-6 t下午04:43:39
 * @Author Ivan
 * @Version 1.0
 */
public class DateTag {
	/**
	 * 处理前台页面时间转换
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param mask
	 *            转换格式
	 * @return
	 */
	public static String convertStrDate(String dateStr, String mask) {
		if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(mask)) {
			return "";
		}
		if(dateStr.length()==12){
			dateStr=dateStr+"00";
		}
		Date date = ConvertUtil.getDate(dateStr);
		if (date == null) {
			return dateStr;
		}
		String str = new SimpleDateFormat(mask).format(date);
		return StringUtils.isNotBlank(str) ? str : dateStr;
	}

	/**
	 * 处理前台页面时间转换 把日期类型转换为指定的格式
	 * 
	 * @author hudaofa
	 * @param date
	 *            日期
	 * @param mask
	 *            转换格式
	 * @return
	 */
	public static String dateToString(Date date, String mask) {
		return date==null?"": DateUtil.dateToString(date, mask);
	}
}
