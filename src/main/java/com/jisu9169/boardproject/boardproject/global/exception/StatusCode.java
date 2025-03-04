package com.jisu9169.boardproject.boardproject.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {

	// 200 번대
	SUCCESS_SIGNUP(HttpStatus.CREATED, "회원가입에 성공했습니다."),
	SUCESS_LOGOUT(HttpStatus.OK, "로그아웃에 성공했습니다."),


	// 400 번대
	ALREADY_EXIST_USER(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다"),
	CHECK_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요."),

	INVALID_TOKEN(HttpStatus.BAD_REQUEST, "검증되지 않은 토큰입니다.");

	private final HttpStatus statusCode;
	private final String message;
}
