package com.iusofts.blades.sys.exception;

/**
 * 
 * 功能描述： 系统错误基础类
 */
public class BaseException extends RuntimeException implements BasicException {
    
    private Integer code;
    
    private String message;
    
    private Object ext;

    public BaseException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseException() {
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }
}
