package com.jisu9169.boardproject.boardproject.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ExceptionResponseDto {

	private String message;
	private String path;
}
