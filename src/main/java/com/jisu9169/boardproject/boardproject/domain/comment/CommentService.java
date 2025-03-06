package com.jisu9169.boardproject.boardproject.domain.comment;

import org.springframework.stereotype.Service;

import com.jisu9169.boardproject.boardproject.domain.comment.dto.CreateCommentRequestDto;
import com.jisu9169.boardproject.boardproject.domain.comment.entity.Comment;
import com.jisu9169.boardproject.boardproject.domain.post.PostService;
import com.jisu9169.boardproject.boardproject.domain.post.entity.Post;
import com.jisu9169.boardproject.boardproject.domain.user.entity.Users;
import com.jisu9169.boardproject.boardproject.global.exception.CustomException;
import com.jisu9169.boardproject.boardproject.global.exception.StatusCode;
import com.jisu9169.boardproject.boardproject.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	private final PostService postService;

	public void createComment(Long postId, CreateCommentRequestDto requestDto, UserDetailsImpl userDetails) {
		Users user = userDetails.getUser();
		Post post = postService.findPostById(postId);

		Comment parent = null;
		if (requestDto.getParentCommentId() != null) {
			parent = commentRepository.findById(requestDto.getParentCommentId()).orElseThrow(
				() -> new CustomException(StatusCode.COMMENT_NOT_FOUND));
		}

		Comment comment = Comment.createComment(requestDto, post, user, parent);
		commentRepository.save(comment);
	}
}
