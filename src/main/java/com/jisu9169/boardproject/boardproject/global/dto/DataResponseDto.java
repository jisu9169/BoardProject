package com.jisu9169.boardproject.boardproject.global.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DataResponseDto<T> {

	private final HttpStatus statusCode;
	private final String message;
	private final T data;

	@Builder
	public DataResponseDto(HttpStatus status, String message, T data) {
		this.statusCode = status;
		this.message = message;
		this.data = data;
	}
}
