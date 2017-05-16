package com.iusofts.blades.sys.web.util;

import java.util.Map;

/**
 * 校验结果
 * 
 * @author：Ivan
 * @date： 2016年2月1日 下午7:31:36
 */
public class ValidationResult {

  /**
   * 校验结果是否有错
   */
  private boolean hasErrors;
  
  /**
   * 校验错误信息(key:对象属性名,value:错误信息)
   */
  private Map<String,String> errorMsg;

  public boolean isHasErrors() {
    return hasErrors;
  }

  public void setHasErrors(boolean hasErrors) {
    this.hasErrors = hasErrors;
  }

  /**
   * 校验错误信息(key:对象属性名,value:错误信息)
   * @return
   * @author：Ivan
   * @date：2016年2月1日 下午7:46:37
   */
  public Map<String, String> getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(Map<String, String> errorMsg) {
    this.errorMsg = errorMsg;
  }

  @Override
  public String toString() {
    return "ValidationResult [hasErrors=" + hasErrors + ", errorMsg="
        + errorMsg + "]";
  }

}