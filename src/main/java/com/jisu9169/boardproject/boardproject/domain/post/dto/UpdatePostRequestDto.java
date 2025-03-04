package com.jisu9169.boardproject.boardproject.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePostRequestDto {

	@NotBlank(message = "제목을 입력해주세요.")
	private String title;
	private String content;

	public UpdatePostRequestDto(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
