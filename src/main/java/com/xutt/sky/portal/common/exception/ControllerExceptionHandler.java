package com.xutt.sky.portal.common.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xutt.sky.portal.common.base.RestResult;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public RestResult resolveBusinessException(BusinessException e) {
		System.out.println(e);
		return new RestResult(1, e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public RestResult resolveException(HttpServletResponse response, Exception e) {
		System.out.println(e);
		response.setStatus(500);
		return new RestResult(1, e.getMessage());
	}
}