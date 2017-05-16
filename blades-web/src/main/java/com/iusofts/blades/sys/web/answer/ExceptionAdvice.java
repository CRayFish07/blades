package com.iusofts.blades.sys.web.answer;

import com.iusofts.blades.sys.common.util.FastJsonUtils;
import com.iusofts.blades.sys.exception.BasicException;
import com.iusofts.blades.sys.web.vo.ExceptionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ExceptionAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
	public ExceptionVo handleException(Exception e) throws Exception {
		ExceptionVo exceptionVo = new ExceptionVo();
		if(e instanceof BasicException){
			BasicException basicException = ((BasicException) e);
			exceptionVo.setCode(basicException.getCode());
			exceptionVo.setMessage(basicException.getMessage());
			exceptionVo.setExt(basicException.getExt());
			LOGGER.error("异常码:{},异常描述:{},扩展信息:{}", exceptionVo.getCode(), exceptionVo.getMessage(), FastJsonUtils.obj2json(basicException.getExt()));
		}else{
			LOGGER.error(e.getMessage(),e);
			throw e;
			//exceptionVo.setCode(1);
			//exceptionVo.setMessage("服务器繁忙");
		}
		return exceptionVo;
	}
	
}
