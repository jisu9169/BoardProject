package com.jisu9169.boardproject.boardproject.domain.post.entity;

import lombok.Getter;

@Getter
public enum PostStatusEnum {

	ACTIVE("게시글 활성화 상태"),
	INACTIVE("게시글 비활성화 상태"),
	DELETED("게시글 삭제 상태");

	private final String description;

	PostStatusEnum(String description) {
		this.description = description;
	}
}
