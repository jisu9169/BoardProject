package com.jisu9169.boardproject.boardproject.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {

	// 200 번대
	SUCCESS_SIGNUP(HttpStatus.CREATED, "회원가입에 성공했습니다.");

	private final HttpStatus statusCode;
	private final String message;
}
