/**
 * 
 */
package com.iusofts.blades.sys.web.util.tags;

import com.iusofts.blades.sys.common.util.StringUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * 
 * @ClassName: StringTag
 * 
 * @Description:
 * 
 * @Date: 2013-5-26下午04:24:57
 * @Author Ivan
 * @Version 1.0
 */
public class StringTag {

	/**
	 * 截取字符串，并可追加省略号
	 * 
	 * @param str
	 *            原字符串
	 * @param startIndex
	 *            截取字符串开始位置的下标，包括该下表下的字符
	 * @param endIndex
	 *            截取字符串结束位置的下标，不包括该下表下的字符
	 * @param bool
	 *            是否在后面追加"..."省略号,true:追加，false：不追加
	 * @return String
	 */
	public static String cutString(String str, String startIndex, String endIndex,
			String bool) {
		if(Integer.valueOf(startIndex) > str.length()){
			return "";
		}
		if(Integer.valueOf(endIndex) > str.length()){
			return str;
		}
		StringBuffer sb = new StringBuffer(str);
		String resultStr = "";
		if ("true".equals(bool)) {
			resultStr = sb.substring(Integer.valueOf(startIndex), Integer.valueOf(endIndex)) + "...";
		} else {
			resultStr = sb.substring(Integer.valueOf(startIndex), Integer.valueOf(endIndex));
		}
		return resultStr;
	}

	/**
	 * 从开始位置截取指定字符长度的字符串，并可追加省略号 (中文算两个字符长度，英文算一个字符长度，如果数字不是偶数，则截取中文字符少取一个字符位)
	 * 
	 * @param str
	 *            原字符串
	 * @param length
	 *            指定字符长度
	 * @param bool
	 *            是否在后面追加"..."省略号,true:追加，false：不追加
	 * @return String
	 */
	public static String cutStringByCharType(String str, String length,
			String bool) {
		int strLength = 0;
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		if (chars.length > 0) {
			if(chars.length>Integer.valueOf(length)){
				for (int i = 0; i < chars.length; i++) {
					strLength += StringUtil.getLength(chars[i]);
					if (strLength > Integer.valueOf(length)) {
						break;
					} else {
						sb.append(chars[i]);
					}
				}
				return "true".equals(bool) ? sb.append("...").toString() : sb.toString();
			}else{
				return str;
			}
		} else {
			return "";
		}
	}
	/**
	 * 获得小数点前几位
	 * 
	 * @return
	 */
	public static String getSubStringBefore(Double param) {

		//NumberFormat nf=NumberFormat.getInstance("#0.000");
		DecimalFormat df=new DecimalFormat("#0");
		df.format(param);
		/*nf.format(param).
		String context=str;
        if(str.indexOf(index)!= -1){
		int a = str.indexOf(index);
		context = str.substring(0, a);*/
     //   }
		return df.format(param);
	}
	
	/**
	 * 计算百分比
	 * 
	 * @return
	 */
	public static String getSubStringByToBFB(String str1, String str2) {
		
		int jj=Integer.valueOf(str1);
		int cj=Integer.valueOf(str2);
		int jc = jj-cj;
		String dbd = null;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		if(jj!=0){
			dbd=nf.format(((float)jc/(float)jj)*100)+"%";
		}else{
			dbd="0%";
		}
		return dbd;
	}
	
	/**
	 * 计算百分比
	 * 
	 * @return
	 */
	public static String getSubStringByToAFA(String str1, String str2) {
		
		int jj=Integer.valueOf(str1);
		int cj=Integer.valueOf(str2);
		
		String dbd = null;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		if(jj!=0){
			dbd=nf.format(((float)jj/(float)cj)*100)+"%";
		}else{
			dbd="0%";
		}
		return dbd;
	}
} 
