package com.jisu9169.boardproject.boardproject.global.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageResponseDto {

	private final HttpStatus statusCode;
	private final String message;

	@Builder
	public MessageResponseDto(HttpStatus status, String message) {
		this.statusCode = status;
		this.message = message;
	}
}
