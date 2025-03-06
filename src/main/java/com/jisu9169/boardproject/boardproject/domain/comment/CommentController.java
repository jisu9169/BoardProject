package com.jisu9169.boardproject.boardproject.domain.comment;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jisu9169.boardproject.boardproject.domain.comment.dto.CreateCommentRequestDto;
import com.jisu9169.boardproject.boardproject.global.dto.MessageResponseDto;
import com.jisu9169.boardproject.boardproject.global.exception.StatusCode;
import com.jisu9169.boardproject.boardproject.global.response.ResponseFactory;
import com.jisu9169.boardproject.boardproject.global.security.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<MessageResponseDto> createComment(
		@PathVariable Long postId, @Valid @RequestBody CreateCommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		commentService.createComment(postId, requestDto, userDetails);
		return ResponseFactory.ok(StatusCode.SUCCESS_CREATE_COMMENT);
	}
}
