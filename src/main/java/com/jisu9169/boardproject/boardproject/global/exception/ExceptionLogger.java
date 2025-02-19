package com.jisu9169.boardproject.boardproject.global.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ExceptionLogger:: ")
public class ExceptionLogger {

	public static void logException(String className, String methodName, String errorMessage ) {
		log.info("ExceptionMethod: {}.{}", className, methodName);
		log.info("ErrorCode: {}", errorMessage);
	}
}
