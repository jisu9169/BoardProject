package com.jisu9169.boardproject.boardproject.global.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j(topic = "CustomException:: ")
public class CustomException extends RuntimeException {

	private final StatusCode statusCode;

	public CustomException(StatusCode statusCode) {
		super(statusCode.getMessage());
		this.statusCode = statusCode;
		logException();
	}

	private void logException() {
		String className = Thread.currentThread().getStackTrace()[3].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		ExceptionLogger.logException(className, methodName, statusCode.getMessage());

	}

}
