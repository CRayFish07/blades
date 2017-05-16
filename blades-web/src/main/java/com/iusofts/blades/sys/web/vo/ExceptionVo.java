package com.iusofts.blades.sys.web.vo;

public class ExceptionVo {

	private Integer code;

	private String message;
	
	private Object ext;

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getExt() {
		return ext;
	}

	public void setExt(Object ext) {
		this.ext = ext;
	}
}
