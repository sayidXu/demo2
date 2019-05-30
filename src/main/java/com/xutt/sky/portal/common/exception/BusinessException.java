package com.xutt.sky.portal.common.exception;

public class BusinessException extends RuntimeException {

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String code, String message) {
		super("{\"code\":\"" + code + "\",\"message\":\"" + message + "\"}");
	}
}
