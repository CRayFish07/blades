package com.iusofts.blades.sys.web.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;

/**
 * 校验工具类
 * 
 * @author：Ivan
 * @date： 2016年2月1日 下午7:31:54
 */
public class ValidationUtils {

	private static Validator validator = Validation
			.buildDefaultValidatorFactory().getValidator();

	/**
	 * 校验对象所有属性
	 * 
	 * @param obj
	 * @param groups
	 *            分组
	 * @return
	 * @author：Ivan
	 * @date：2016年2月1日 下午7:43:03
	 */
	public static <T> ValidationResult validateEntity(T obj, Class<?>... groups) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = null;
		if (groups != null && groups.length>0) {
			set=validator.validate(obj, groups);
		} else {
			set=validator.validate(obj, Default.class);
		}
		if (CollectionUtils.isNotEmpty(set)) {
			result.setHasErrors(true);
			Map<String, String> errorMsg = new HashMap<String, String>();
			for (ConstraintViolation<T> cv : set) {
				errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
			}
			result.setErrorMsg(errorMsg);
		}
		return result;
	}

	/**
	 * 校验对象的单个属性
	 * 
	 * @param obj
	 * @param propertyName
	 * @return
	 * @author：Ivan
	 * @date：2016年2月1日 下午7:43:55
	 */
	public static <T> ValidationResult validateProperty(T obj,
			String propertyName, Class<?>... groups) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = null;
		if (groups != null && groups.length>0) {
			set=validator.validateProperty(obj, propertyName, groups);
		} else {
			set=validator.validateProperty(obj, propertyName, Default.class);
		}
		if (CollectionUtils.isNotEmpty(set)) {
			result.setHasErrors(true);
			Map<String, String> errorMsg = new HashMap<String, String>();
			for (ConstraintViolation<T> cv : set) {
				errorMsg.put(propertyName, cv.getMessage());
			}
			result.setErrorMsg(errorMsg);
		}
		return result;
	}

}