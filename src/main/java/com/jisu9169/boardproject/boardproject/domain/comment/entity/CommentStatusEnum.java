package com.jisu9169.boardproject.boardproject.domain.comment.entity;

import com.jisu9169.boardproject.boardproject.global.exception.CustomException;
import com.jisu9169.boardproject.boardproject.global.exception.StatusCode;

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

	public static CommentStatusEnum fromStatus(String status) {
		if(status != null) {
			for(CommentStatusEnum statusEnum:CommentStatusEnum.values()) {
				if(statusEnum.name().equals(status)) {
					return statusEnum;
				}
			}
		}
		throw new CustomException(StatusCode.INVALID_COMMENT_STATUS);
	}
}
