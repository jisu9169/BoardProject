package com.jisu9169.boardproject.boardproject.domain.post;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jisu9169.boardproject.boardproject.domain.post.dto.CreatePostRequestDto;
import com.jisu9169.boardproject.boardproject.domain.post.dto.PostResponseDto;
import com.jisu9169.boardproject.boardproject.domain.post.dto.UpdatePostRequestDto;
import com.jisu9169.boardproject.boardproject.domain.post.entity.Post;
import com.jisu9169.boardproject.boardproject.domain.user.entity.Users;
import com.jisu9169.boardproject.boardproject.global.exception.CustomException;
import com.jisu9169.boardproject.boardproject.global.exception.StatusCode;
import com.jisu9169.boardproject.boardproject.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	@Transactional
	public void createPost(CreatePostRequestDto requestDto, UserDetailsImpl userDetails) {
		Users user = userDetails.getUser();
		Post post = Post.createPost(requestDto, user);
		postRepository.save(post);
	}

	@Transactional(readOnly = true)
	public Page<PostResponseDto> getPosts(int page, int size, String sortBy, boolean isAsc) {

		Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Post> postPage = postRepository.findAll(pageable);

		return postPage.map(PostResponseDto::from);
	}

	@Transactional
	public void updatePost(Long postId, UpdatePostRequestDto requestDto, UserDetailsImpl userDetails) {
		Post post = findPostById(postId);

		if(!Objects.equals(post.getUsers().getUserId(), userDetails.getUser().getUserId())) {
			throw new CustomException(StatusCode.POST_EDIT_FORBIDDEN);
		}

		if(requestDto.getTitle() != null) {
			post.updateTitle(requestDto.getTitle());
		}

		if(requestDto.getContent() != null) {
			post.updateContent(requestDto.getContent());
		}

		postRepository.save(post);
	}

	public PostResponseDto getPostById(Long postId) {
		Post post = findPostById(postId);
		return PostResponseDto.from(post);
	}


	public Post findPostById(Long postId){
		return postRepository.findById(postId).orElseThrow(() -> new CustomException(StatusCode.POST_NOT_FOUND));
	}
}
