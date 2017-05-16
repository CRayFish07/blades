package com.iusofts.blades.sys.exception;

import java.io.Serializable;


public interface BasicException extends Serializable{
	
	public Integer getCode();
	
	public String getMessage();
	
	public Object getExt();

}
