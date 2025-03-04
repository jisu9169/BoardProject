package com.jisu9169.boardproject.boardproject.domain.comment.entity;

import com.jisu9169.boardproject.boardproject.domain.common.Timestamped;
import com.jisu9169.boardproject.boardproject.domain.post.entity.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@ManyToOne(fetch = FetchType.LAZY) // ✅ 다대일 관계
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@Builder
	public Comment(String content, Post post) {
		this.content = content;
		this.post = post;
	}
}
