package com.iusofts.blades.sys.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

/**
 * 类型转换
 * 
 * @ClassName: (ConvertUtil.java)
 * 
 * @Description:
 * 
 * @Date: 2011-8-24 上午02:52:04
 * @Author chymilk
 * @Version 1.0
 */
public final class ConvertUtil {

	private final static String deafult_encode = "UTF-8";
	static {
		final String patterns[] = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
				"yyyy-MM-dd HH:mm", "yyyyMMdd", "yyyyMMddmm", "yyyyMMddHHmmss" };
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(patterns);
		ConvertUtils.register(dc, Date.class);
	}

	private ConvertUtil() {
	}

	public static int getInteger(Object obj) {
		return (Integer) ConvertUtils.convert(obj, Integer.class);
	}

	public static String getString(Object obj) {
		return ConvertUtils.convert(obj);
	}

	public static boolean getBoolean(Object obj) {
		return (Boolean) ConvertUtils.convert(obj, Boolean.class);
	}

	public static Date getDate(Object obj) {
		return (Date) ConvertUtils.convert(obj, Date.class);
	}
	
	public static Long getLong(Object obj) {
		return (Long) ConvertUtils.convert(obj, Long.class);
	}

	public static String toLowerCase(String str) {
		return str.trim().toLowerCase();
	}

	public static String toUpperCase(String str) {
		return str.trim().toUpperCase();
	}

	public static void main(String[] arg) {
		Object obj = "20111205010101";
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(getDate(obj)));
	}
	
	public static String blob2String(Object obj) {
		return blob2String(obj, deafult_encode);
	}
	
	public static String blob2String(Object obj, String encode) {
		Blob blob = (Blob) obj;
		if (blob == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		try {
			inputStream = blob.getBinaryStream();
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, encode));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static byte[] blob2Img(Object obj){
		Blob blob = (Blob) obj;
		byte[] data = null;
		InputStream inStream;
		try {
			inStream = blob.getBinaryStream();
			try{
				long nLen = blob.length();
				int nSize =(int)nLen;
				data = new byte[nSize];
				inStream.read(data);
				inStream.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return data;
	}
	
}
