package com.jisu9169.boardproject.boardproject.domain.comment.dto;

import java.time.LocalDateTime;

import com.jisu9169.boardproject.boardproject.domain.comment.entity.Comment;
import com.jisu9169.boardproject.boardproject.domain.comment.entity.CommentStatusEnum;
import com.jisu9169.boardproject.boardproject.domain.user.dto.UserResponseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseDto {

	private Long commentId;
	private String content;
	private UserResponseDto userResponseDto;
	private CommentStatusEnum commentStatus;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;

	@Builder
	public CommentResponseDto(Long commentId, String content, UserResponseDto userResponseDto, CommentStatusEnum commentStatus,
		LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
		this.commentId = commentId;
		this.content = content;
		this.userResponseDto = userResponseDto;
		this.commentStatus = commentStatus;
		this.createdAt = createdAt;
		this.lastModifiedAt = lastModifiedAt;
	}

	public static CommentResponseDto from(Comment comment) {
		return CommentResponseDto.builder()
			.commentId(comment.getCommentId())
			.content(comment.getContent())
			.userResponseDto(UserResponseDto.from(comment.getUser()))
			.commentStatus(comment.getCommentStatusEnum())
			.createdAt(comment.getCreatedAt())
			.lastModifiedAt(comment.getLastModifiedAt())
			.build();
	}
}
