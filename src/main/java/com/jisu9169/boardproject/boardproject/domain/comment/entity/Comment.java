package com.jisu9169.boardproject.boardproject.domain.comment.entity;

import com.jisu9169.boardproject.boardproject.domain.comment.dto.CreateCommentRequestDto;
import com.jisu9169.boardproject.boardproject.domain.comment.dto.UpdateCommentRequestDto;
import com.jisu9169.boardproject.boardproject.domain.common.Timestamped;
import com.jisu9169.boardproject.boardproject.domain.post.entity.Post;
import com.jisu9169.boardproject.boardproject.domain.user.entity.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_comment_id")
	private Comment parent;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private CommentStatusEnum commentStatusEnum;

	@Builder
	public Comment(String content, Post post, Comment parent, Users user, CommentStatusEnum commentStatusEnum) {
		this.content = content;
		this.post = post;
		this.parent = parent;
		this.user = user;
		this.commentStatusEnum = commentStatusEnum;
	}

	public static Comment createComment(CreateCommentRequestDto requestDto, Post post, Users user,
		Comment parent) {
		return Comment.builder()
			.content(requestDto.getContent())
			.post(post)
			.parent(parent)
			.user(user)
			.commentStatusEnum(CommentStatusEnum.ACTIVE)
			.build();
	}

	public void updateContent(UpdateCommentRequestDto requestDto) {
		if (requestDto.getContent() != null) {
			this.content = requestDto.getContent();
		}
		if (requestDto.getCommentStatus() != null) {
			this.commentStatusEnum = CommentStatusEnum.fromStatus(requestDto.getCommentStatus());
		}
	}

	public void disableComment() {
		this.commentStatusEnum = CommentStatusEnum.INACTIVE;
	}
}
