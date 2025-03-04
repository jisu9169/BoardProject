package com.jisu9169.boardproject.boardproject.domain.post.entity;

import java.util.List;
import java.util.ArrayList;

import com.jisu9169.boardproject.boardproject.domain.comment.entity.Comment;
import com.jisu9169.boardproject.boardproject.domain.common.Timestamped;
import com.jisu9169.boardproject.boardproject.domain.post.dto.CreatePostRequestDto;
import com.jisu9169.boardproject.boardproject.domain.user.entity.Users;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

	@ManyToOne(fetch = FetchType.LAZY) // 여러 개의 게시글이 하나의 유저에 속함 (다대일 관계)
	@JoinColumn(name = "user_id", nullable = false)
	private Users users;

	@Column(nullable = false)
	private String title;

	@Column(length = 1000)
	private String body;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private PostStatusEnum postStatus;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@Builder
	public Post(Users users, String title, String body, PostStatusEnum postStatus ) {
		this.users = users;
		this.title = title;
		this.body = body;
		this.postStatus = postStatus;
	}

	public static Post createPost(CreatePostRequestDto requestDto, Users users) {
		return Post.builder()
			.users(users)
			.title(requestDto.getTitle())
			.body(requestDto.getContent())
			.postStatus(PostStatusEnum.ACTIVE)
			.build();
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateContent(String content) {
		this.body = content;
	}

}
