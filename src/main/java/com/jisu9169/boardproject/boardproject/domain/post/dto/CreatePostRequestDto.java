package com.jisu9169.boardproject.boardproject.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreatePostRequestDto {

	@NotBlank(message = "제목은 필수 입력 값입니다.") // 공백 & null 방지
	@Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
	private final String title;
	@Size(max = 1000, message = "본문 내용은 1,000자 이하로 입력해주세요.")
	private final String content;

	public CreatePostRequestDto(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
