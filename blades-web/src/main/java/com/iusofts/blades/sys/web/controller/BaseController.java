package com.iusofts.blades.sys.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iusofts.blades.sys.exception.ValidationException;
import com.iusofts.blades.sys.web.util.ConvertUtil;
import com.iusofts.blades.sys.web.util.ValidationUtils;
import com.iusofts.blades.sys.web.util.ValidationResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.iusofts.blades.sys.common.util.FastJsonUtils;

/**
 * @ClassName: 基本控制类 (BaseController.java)
 * 
 * @Description: 提供一些在控制层中常用的方法
 * 
 * @Date: 2011-6-29 下午07:04:57
 * @Author chymilk
 * @Version 1.0
 */
/**
 * @ClassName: (BaseController.java)
 * 
 * @Description:添加表单校验方法
 * 
 * @Date: 2016年2月17日 上午9:58:48
 * @Author Ivan
 * @Version 2.0
 */
@Controller
public class BaseController {

	private static final Log logger = LogFactory.getLog(BaseController.class);

	public BaseController() {
	}

	protected static void flushResponse(HttpServletResponse response,
			Object responseContent) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(ConvertUtil.getString(responseContent));
		} catch (IOException e) {
			logger.error(e);
		} finally {
			writer.flush();
			writer.close();
		}

	}

	protected static void flushResponseMsg(HttpServletResponse response,
			boolean success, String msg) {

		Map<String, Object> map = new HashMap<>();
		map.put("success", success);
		map.put("msg", msg);
		flushResponse(response, FastJsonUtils.obj2json(map));
	}

	/**
	 * <p>
	 * 从request中获取参数根据键值对形成Map. <br>
	 * 注意:对于数组参数，只拷贝了第1个元素.<br>
	 * 对于全空格的数据，仍然保留，在保存修改时可能需要.
	 * </p>
	 * 
	 * @param request
	 * @return map
	 */
	protected static Map<String, String> getParameter(HttpServletRequest request) {
		Map<String, String> paramValue = new HashMap<String, String>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			String value = request.getParameter(key);
			paramValue.put(key, value);
		}
		return paramValue;
	}

	/**
	 * <p>
	 * 从request中获取参数根据键值对形成Map. <br>
	 * 对于全空格的数据，仍然保留，在保存修改时可能需要.
	 * </p>
	 * 
	 * @param request
	 * @return map
	 */
	protected static Map<String, String[]> getParameterValues(
			HttpServletRequest request) {
		Map<String, String[]> paramValues = new HashMap<String, String[]>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			String value[] = request.getParameterValues(key);
			paramValues.put(key, value);
		}
		return paramValues;
	}

	/**
	 * 获得当前页码
	 * 
	 * @param request
	 * @return int
	 */
	protected static int getPageNo(HttpServletRequest request) {
		int pageNum = ConvertUtil.getInteger(request.getParameter("pageNo"));
		return pageNum != 0 ? pageNum : 1;
	}

	/**
	 * 获得每页大小
	 * 
	 * @param request
	 * @return int
	 */
	protected static int getPageSize(HttpServletRequest request) {
		int numPerPage = ConvertUtil.getInteger(request
				.getParameter("pageSize"));
		return numPerPage != 0 ? numPerPage : 10;
	}

	/**
	 * 获得总数
	 * 
	 * @param request
	 * @return int
	 */
	protected static int getTotalCount(HttpServletRequest request) {
		return ConvertUtil.getInteger(request.getParameter("totalCount"));
	}


	/**
	 * 返回操作成功
	 * 
	 * @param response
	 * @author：Ivan
	 * @date：2016年2月23日 下午2:07:56
	 */
	protected void returnSuccess(HttpServletResponse response) {
		flushResponse(response, "success");
	}
	
	/**
	 * 返回操作失败
	 * 
	 * @param response
	 * @author：Ivan
	 * @date：2016年2月23日 下午2:07:56
	 */
	protected void returnError(HttpServletResponse response) {
		flushResponse(response, "error");
	}

	/**
	 * 获取单个校验属性名
	 * 
	 * @param request
	 * @return
	 * @author：Ivan
	 * @date：2016年2月2日 下午2:03:44
	 */
	protected String getValidateProperty(HttpServletRequest request) {
		return request.getParameter("validateProperty");
	}

	/**
	 * 是否校验单个属性
	 * 
	 * @param request
	 * @return
	 * @author：Ivan
	 * @date：2016年2月2日 下午2:07:20
	 */
	protected boolean isValidateProperty(HttpServletRequest request) {
		if (request!=null && StringUtils.isNotBlank(getValidateProperty(request))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否校验某个属性
	 * 
	 * @description 用于自定义校验时避免重复校验造成的资源浪费,推荐使用,但不是必须的
	 * @param request
	 * @param property 需要校验的属性
	 * @return
	 * @author：Ivan
	 * @date：2016年2月2日 下午2:07:20
	 */
	protected boolean isValidateProperty(HttpServletRequest request,
			String property) {
		if (StringUtils.isNotBlank(getValidateProperty(request))
				&& property.equals(getValidateProperty(request))) {
			return true;
		} else if(StringUtils.isBlank(getValidateProperty(request))){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 表单验证
	 *
	 * @param obj
	 * @return
	 * @author：Ivan
	 * @date：2016年2月2日 下午2:19:02
	 */
	protected boolean validate(Object obj, Class<?>... groups) throws ValidationException {
		return validate(obj, null, new HashMap<String, String>(),
				groups);
	}
	
	/**
	 * 表单验证
	 * 
	 * @param obj
	 * @param request
	 * @return
	 * @author：Ivan
	 * @date：2016年2月2日 下午2:19:02
	 */
	protected boolean validate(Object obj, HttpServletRequest request, Class<?>... groups) throws ValidationException {
		return validate(obj, request, new HashMap<String, String>(),
				groups);
	}

	/**
	 * 表单验证
	 * 
	 * @param obj
	 *            需要校验的对象
	 * @param request
	 * @param otherErrors
	 *            其它错误信息
	 * @return
	 * @author：Ivan
	 * @date：2016年2月2日 下午2:19:02
	 */
	protected boolean validate(Object obj, HttpServletRequest request, Map<String, String> otherErrors,
			Class<?>... groups) throws ValidationException {
		ValidationResult result = null;
		if(request!=null) {
			if (isValidateProperty(request)) {
				if (hasProperty(obj, getValidateProperty(request))) {
					// 校验单个属性
					result = ValidationUtils.validateProperty(obj,
							getValidateProperty(request));
				} else {
					result = new ValidationResult();
				}

			} else {
				// 校验整个对象
				result = ValidationUtils.validateEntity(obj, groups);
			}

			// 校验再次输入是否一致
			Map<String, String> confirmErrors = new HashMap<>();
			Map<String, String> pMap = getParameter(request);
			for (String key : pMap.keySet()) {
				if (key.indexOf(":") != -1) {
					String str[] = key.split(":");
					String c1 = pMap.get(str[1]) == null ? "" : pMap.get(str[1]);
					String c2 = pMap.get(key) == null ? "" : pMap.get(key);
					if (!c1.equals(c2)) {
						confirmErrors.put(key, "输入不一致");
					}
				}
			}

			// 添加校验再次输入不一致错误
			if (confirmErrors != null && confirmErrors.size() > 0) {
				// 汇总到其它错误信息中
				otherErrors.putAll(confirmErrors);
			}
		}else{
			// 校验整个对象
			result = ValidationUtils.validateEntity(obj, groups);
		}

		// 将其它错误信息汇总到校验结果中
		if (otherErrors != null && otherErrors.size() > 0) {
			result.setHasErrors(true);
			if (result.getErrorMsg() == null) {
				result.setErrorMsg(otherErrors);
			} else {
				result.getErrorMsg().putAll(otherErrors);
			}
		}

		if (result.isHasErrors()) {
			throw new ValidationException(result.getErrorMsg());
		}else{
			if (isValidateProperty(request)) {
				//单属性校验拦截
				return false;
			}
		}
		
		return true;
	}
	

	/**
	 * 首字母转小写
	 * 
	 * @param s
	 * @return
	 * @author：Ivan
	 * @date：2016年2月16日 下午5:52:08
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	/**
	 * 首字母转大写
	 * 
	 * @param s
	 * @return
	 * @author：Ivan
	 * @date：2016年2月16日 下午5:52:14
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	/**
	 * 判断对象是否包含某个属性
	 * 
	 * @param property
	 * @return
	 * @author：Ivan
	 * @date：2016年2月17日 上午9:50:21
	 */
	protected boolean hasProperty(Object obj, String property) {
		Class<?> class1 = obj.getClass();// 需要检测的类
		String fieldname = property;// 需要检测的属性名称
		/**
		 * 循环遍历所有的元素，检测有没有这个名字
		 */
		Field[] fields = class1.getDeclaredFields();//当前类元素
		Field[] fields2 = class1.getSuperclass().getDeclaredFields();//父类元素

		boolean b = false;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(fieldname)) {
				b = true;
				break;
			}
		}
		for (int i = 0; i < fields2.length; i++) {
			if (fields2[i].getName().equals(fieldname)) {
				b = true;
				break;
			}
		}

		return b;
	}
}
