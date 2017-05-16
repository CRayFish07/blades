/*
 * Copyright (C) 2009-2017 pashanhoo All rights reserved
 * Author: Ivan Shen
 * Date: 2017/4/20
 * Description:ValidationException.java
 */
package com.iusofts.blades.sys.exception;

/**
 * 校验异常
 *
 * @author Ivan Shen
 */
public class ValidationException extends BaseException {
	public ValidationException(Object errorMsg) {
	    this.setCode(100);
	    this.setMessage("请求参数错误");
	    this.setExt(errorMsg);
	}
}
