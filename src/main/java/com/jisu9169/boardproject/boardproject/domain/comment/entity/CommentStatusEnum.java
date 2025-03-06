package com.jisu9169.boardproject.boardproject.domain.comment.entity;

import lombok.Getter;

@Getter
public enum CommentStatusEnum {

	ACTIVE("댓글 활성화 상태"),
	INACTIVE("댓글 비활성화 상태"),
	DELETED("댓글 삭제 상태");

	private final String description;

	CommentStatusEnum(String description) {
		this.description = description;
	}
}
