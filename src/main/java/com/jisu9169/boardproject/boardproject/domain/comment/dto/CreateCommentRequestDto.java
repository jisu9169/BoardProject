package com.jisu9169.boardproject.boardproject.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

	@NotBlank(message = "입력된 내용이 없습니다.")
	@Size(min = 1, max = 100, message = "댓글은 1자 이상, 100자 이하로 입력해주세요.")
	private String content;
	private Long parentCommentId;
}
