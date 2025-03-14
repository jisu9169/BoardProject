package com.jisu9169.boardproject.boardproject.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {

	// 200 번대
	SUCCESS_SIGNUP(HttpStatus.CREATED, "회원가입에 성공했습니다."),
	SUCCESS_LOGOUT(HttpStatus.OK, "로그아웃에 성공했습니다."),

	SUCCESS_CREATE_POST(HttpStatus.CREATED, "게시물 생성에 성공했습니다."),
	SUCCESS_GET_POST(HttpStatus.OK, "게시물 조회에 성공했습니다."),
	SUCCESS_UPDATE_POST(HttpStatus.OK, "게시물 수정에 성공했습니다."),
	SUCCESS_DELETE_POST(HttpStatus.OK, "게시물 삭제에 성공했습니다."),

	SUCCESS_CREATE_COMMENT(HttpStatus.CREATED, "댓글 생성에 성공했습니다."),
	SUCCESS_GET_COMMENT(HttpStatus.OK, "댓글 조회에 성공했습니다."),
	SUCCESS_UPDATE_COMMENT(HttpStatus.OK, "댓글 수정에 성공했습니다."),
	SUCCESS_DELETE_COMMENT(HttpStatus.OK, "댓글 삭제에 성공했습니다."),


	// 400 번대
	ALREADY_EXIST_USER(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
	CHECK_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요."),
	INVALID_TOKEN(HttpStatus.BAD_REQUEST, "검증되지 않은 토큰입니다."),

	POST_EDIT_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 게시글을 수정할 권한이 없습니다."),
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),

	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
	COMMENT_USER_MISMATCH(HttpStatus.FORBIDDEN, "댓글 작성자만 수정할 수 있습니다."),
	INVALID_COMMENT_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 ENUM 값입니다.");
	private final HttpStatus statusCode;
	private final String message;
}
