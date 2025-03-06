package com.jisu9169.boardproject.boardproject.domain.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jisu9169.boardproject.boardproject.domain.comment.dto.CommentResponseDto;
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

	@Transactional
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

	@Transactional(readOnly = true)
	public Page<CommentResponseDto> getComments(Long postId, int page, int size, String sortBy, boolean isAsc, Long commentId) {
		Post post = postService.findPostById(postId);
		Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Comment comment = commentId ==null ? null : getCommentById(commentId);
		Page<Comment> pageComment = commentRepository.findByPostAndParent(post, comment, pageable);

		return pageComment.map(CommentResponseDto::from);
	}

	public Comment getCommentById(Long commentId) {
		return commentRepository.findById(commentId).orElseThrow(
			() -> new CustomException(StatusCode.COMMENT_NOT_FOUND)
		);
	}
}
