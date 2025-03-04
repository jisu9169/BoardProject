package com.jisu9169.boardproject.boardproject.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jisu9169.boardproject.boardproject.domain.post.dto.CreatePostRequestDto;
import com.jisu9169.boardproject.boardproject.domain.post.dto.PostResponseDto;
import com.jisu9169.boardproject.boardproject.domain.post.dto.UpdatePostRequestDto;
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
public class PostController {

	private final PostService postService;

	@PostMapping("/posts")
	public ResponseEntity<MessageResponseDto> createPost(@Valid @RequestBody CreatePostRequestDto requestDto, UserDetailsImpl userDetails) {
		postService.createPost(requestDto, userDetails);
		return ResponseFactory.created(StatusCode.SUCCESS_CREATE_POST);
	}

	@GetMapping("/posts")
	public ResponseEntity<DataResponseDto<Page<PostResponseDto>>> getPosts(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "createdAt") String sortBy,
		@RequestParam(defaultValue = "false") boolean isAsc) {
		Page<PostResponseDto> posts = postService.getPosts(page, size, sortBy, isAsc);
		return ResponseFactory.ok(posts, StatusCode.SUCCESS_GET_POST);
	}

	@GetMapping("/posts/{postId}")
	public ResponseEntity<DataResponseDto<PostResponseDto>> getPostById(@PathVariable Long postId) {
		return ResponseFactory.ok(postService.getPostById(postId), StatusCode.SUCCESS_GET_POST);
	}

	@PatchMapping("/posts/{postId}")
	public ResponseEntity<MessageResponseDto> updatePost(
		@PathVariable Long postId , @RequestBody UpdatePostRequestDto requestDto, UserDetailsImpl userDetails) {
		postService.updatePost(postId, requestDto, userDetails);
		return ResponseFactory.ok(StatusCode.SUCCESS_UPDATE_POST);
	}

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<MessageResponseDto> deletePost(@PathVariable Long postId, UserDetailsImpl userDetails) {
		postService.deletePost(postId, userDetails);
		return ResponseFactory.ok(StatusCode.SUCCESS_DELETE_POST);
	}
}
