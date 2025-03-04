package com.jisu9169.boardproject.boardproject.domain.post.dto;

import java.time.LocalDateTime;

import com.jisu9169.boardproject.boardproject.domain.post.entity.Post;
import com.jisu9169.boardproject.boardproject.domain.post.entity.PostStatusEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDto {

	private Long postId;
	private String username;
	private String title;
	private String content;
	private PostStatusEnum postStatus;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;

	@Builder
	public PostResponseDto(Long postId, String title, String content, String username, PostStatusEnum postStatus, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.username = username;
		this.postStatus = postStatus;
		this.createdAt = createdAt;
		this.lastModifiedAt = lastModifiedAt;
	}

	public static PostResponseDto from(Post post) {
		return PostResponseDto.builder()
			.postId(post.getPostId())
			.title(post.getTitle())
			.content(post.getBody())
			.username(post.getUsers().getUsername())
			.postStatus(post.getPostStatus())
			.createdAt(post.getCreatedAt())
			.lastModifiedAt(post.getLastModifiedAt())
			.build();
	}
}
