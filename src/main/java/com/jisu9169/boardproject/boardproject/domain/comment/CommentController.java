package com.jisu9169.boardproject.boardproject.domain.comment;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jisu9169.boardproject.boardproject.domain.comment.dto.CommentResponseDto;
import com.jisu9169.boardproject.boardproject.domain.comment.dto.CreateCommentRequestDto;
import com.jisu9169.boardproject.boardproject.domain.comment.dto.UpdateCommentRequestDto;
import com.jisu9169.boardproject.boardproject.global.dto.DataResponseDto;
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

	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<DataResponseDto<Page<CommentResponseDto>>> getComments(
		@PathVariable Long postId,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "createdAt") String sortBy,
		@RequestParam(defaultValue = "false") boolean isAsc,
		@RequestParam(defaultValue = "null") Long commentId) {
		Page<CommentResponseDto> responseDto = commentService.getComments(postId, page, size, sortBy, isAsc, commentId);
		return ResponseFactory.ok(responseDto, StatusCode.SUCCESS_GET_COMMENT);
	}

	@PatchMapping("/posts/{postId}/comments/{commentsId}")
	public ResponseEntity<MessageResponseDto> updateComment(
		@PathVariable Long postId, @PathVariable Long commentsId, @AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody UpdateCommentRequestDto requestDto) {
		commentService.updateComment(postId, commentsId, userDetails, requestDto);
		return ResponseFactory.ok(StatusCode.SUCCESS_UPDATE_COMMENT);
	}

	@DeleteMapping("/posts/{postId}/comments/{commentsId}")
	public ResponseEntity<MessageResponseDto> deleteComment(
		@PathVariable Long postId, @PathVariable Long commentsId, @AuthenticationPrincipal UserDetailsImpl userDetails){
		commentService.deleteComment(postId, commentsId, userDetails);
		return ResponseFactory.ok(StatusCode.SUCCESS_UPDATE_COMMENT);
	}
}
