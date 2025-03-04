package com.jisu9169.boardproject.boardproject.domain.post;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jisu9169.boardproject.boardproject.domain.post.dto.CreatePostRequestDto;
import com.jisu9169.boardproject.boardproject.domain.post.dto.PostResponseDto;
import com.jisu9169.boardproject.boardproject.domain.post.dto.UpdatePostRequestDto;
import com.jisu9169.boardproject.boardproject.domain.post.entity.Post;
import com.jisu9169.boardproject.boardproject.domain.post.entity.PostStatusEnum;
import com.jisu9169.boardproject.boardproject.domain.user.entity.Users;
import com.jisu9169.boardproject.boardproject.global.exception.CustomException;
import com.jisu9169.boardproject.boardproject.global.exception.StatusCode;
import com.jisu9169.boardproject.boardproject.global.security.UserDetailsImpl;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

	@InjectMocks
	private PostService postService;

	@Mock
	private PostRepository postRepository;

	@Mock
	private UserDetailsImpl userDetails;

	@Mock
	private Users user;

	@Test
	@DisplayName("생성 성공 저장 확인")
	void createPost_SUCCESS() {
		// given
		CreatePostRequestDto requestDto = new CreatePostRequestDto("제목", "내용");
		given(userDetails.getUser()).willReturn(user);
		// when
		postService.createPost(requestDto, userDetails);
		// then
		ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(Post.class);
		verify(postRepository, times(1)).save(captor.capture());

		Post savedPost = captor.getValue();

		assertThat(savedPost.getTitle()).isEqualTo("제목");
		assertThat(savedPost.getBody()).isEqualTo("내용");
		assertThat(savedPost.getUsers()).isEqualTo(user);
		assertThat(savedPost.getPostStatus()).isEqualTo(PostStatusEnum.ACTIVE);

		assertThat(savedPost.getTitle()).isNotEqualTo("테스트 제목");
		assertThat(savedPost.getTitle()).isNotEqualTo("테스트 내용");
		assertThat(savedPost.getPostStatus()).isNotEqualTo(PostStatusEnum.DELETED);
	}

	@Test
	@DisplayName("게시물 단건 조회 성공")
	void getPostById_SUCCESS() {
		// given
		Post post = Post.builder()
			.users(user)
			.title("제목")
			.body("내용")
			.postStatus(PostStatusEnum.ACTIVE)
			.build();
		Long postId = 1L;

		given(postRepository.findById(postId)).willReturn(Optional.of(post));

		// when
		PostResponseDto response = postService.getPostById(postId);

		// then
		assertNotNull(response);
		assertEquals("제목", response.getTitle());
		assertNotEquals("테스트 제목", response.getTitle());
		assertEquals("내용", response.getContent());
		assertNotEquals("테스트 내용", response.getContent());
		assertEquals(PostStatusEnum.ACTIVE, response.getPostStatus());
		assertNotEquals(PostStatusEnum.DELETED, response.getPostStatus());
	}

	@Test
	@DisplayName("게시물 단건 조회 실패")
	void getPostById_FAIL() {
		//  given
		Long postId = 1L;
		given(postRepository.findById(postId)).willReturn(Optional.empty());
		// when & then
		assertThrows(CustomException.class, () -> postService.getPostById(postId));
	}

	@Test
	@DisplayName("게시물 페이지 조회 성공")
	void getPosts_SUCCESS() {
		// given
		int page = 0;
		int size = 10;
		String sortBy = "createdAt";
		boolean isAsc = false;

		List<Post> postList = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			postList.add(Post.builder()
				.users(user)
				.title("제목 " + i)
				.body("내용 " + i)
				.postStatus(PostStatusEnum.ACTIVE)
				.build());
		}

		Page<Post> postPage = new PageImpl<>(postList, PageRequest.of(page, size, Sort.by(sortBy).descending()),
			postList.size());

		given(postRepository.findAll(any(Pageable.class))).willReturn(postPage);

		// when
		Page<PostResponseDto> response = postService.getPosts(page, size, sortBy, isAsc);

		// then
		assertNotNull(response);
		assertEquals(size, response.getContent().size());
		assertEquals(page, response.getNumber());
		assertEquals(postList.size(), response.getTotalElements());
	}

	@Test
	@DisplayName("게시물 페이지 조회 실패 - 게시물이 없는 경우")
	void getPosts_FAIL_NoPosts() {
		// given
		int page = 0;
		int size = 10;
		String sortBy = "createdAt";
		boolean isAsc = false;

		Page<Post> emptyPage = Page.empty();

		given(postRepository.findAll(any(Pageable.class))).willReturn(emptyPage);

		// when & then
		assertThrows(CustomException.class, () -> postService.getPosts(page, size, sortBy, isAsc));
	}

	@Test
	@DisplayName("게시물 페이지 조회 실패 - 존재하지 않는 페이지 요청")
	void getPosts_FAIL_InvalidPageNumber() {
		// given
		int page = 100; // 존재하지 않는 페이지 번호
		int size = 10;
		String sortBy = "createdAt";
		boolean isAsc = false;

		Page<Post> emptyPage = Page.empty();

		given(postRepository.findAll(any(Pageable.class))).willReturn(emptyPage);

		// when & then
		assertThrows(CustomException.class, () -> postService.getPosts(page, size, sortBy, isAsc));
	}

	@Test
	@DisplayName("게시물 삭제 성공")
	void deletePost_SUCCESS() {
		// given
		Long postId = 1L;
		UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
		Users user = mock(Users.class);
		given(user.getUserId()).willReturn(1L);
		given(userDetails.getUser()).willReturn(user);
		Post post = Post.builder()
			.users(user)
			.title("제목")
			.body("내용")
			.postStatus(PostStatusEnum.ACTIVE)
			.build();

		given(postRepository.findById(postId)).willReturn(Optional.of(post));
		willDoNothing().given(postRepository).delete(post);

		// when
		postService.deletePost(postId, userDetails);

		// then
		verify(postRepository, times(1)).findById(postId);
		verify(postRepository, times(1)).delete(post);
	}

	@Test
	@DisplayName("게시물 삭제 실패 - 존재하지 않는 게시물")
	void deletePost_FAIL_PostNotFound() {
		// given
		Long postId = 1L;
		UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
		Users user = mock(Users.class);
		given(postRepository.findById(postId)).willReturn(Optional.empty());

		// when & then
		assertThrows(CustomException.class, () -> postService.deletePost(postId, userDetails));

		verify(postRepository, times(1)).findById(postId);
		verify(postRepository, never()).delete(any(Post.class));
	}

	@Test
	@DisplayName("게시물 수정 성공")
	void updatePost_SUCCESS() {
		// given
		UpdatePostRequestDto requestDto = new UpdatePostRequestDto("수정된 제목", "수정된 내용");
		Long postId = 1L;
		UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
		Users user = mock(Users.class);
		Post post = Post.builder()
			.users(user)
			.title("제목")
			.body("내용")
			.postStatus(PostStatusEnum.ACTIVE)
			.build();

		given(user.getUserId()).willReturn(1L);
		given(userDetails.getUser()).willReturn(user);
		given(postRepository.findById(postId)).willReturn(Optional.ofNullable(post));

		// when
		assertDoesNotThrow(() -> postService.updatePost(postId, requestDto, userDetails));
		// then
		assertEquals("수정된 제목", Objects.requireNonNull(post).getTitle());
		assertEquals("수정된 내용", post.getBody());
		verify(postRepository, times(1)).findById(postId);
	}

	@Test
	@DisplayName("게시물 수정 실패 - 게시물 없음")
	void updatePost_FAIL_PostNotFound() {
		// given
		UpdatePostRequestDto requestDto = new UpdatePostRequestDto("수정된 제목", "수정된 내용");
		Long postId = 1L;
		UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
		Users user = mock(Users.class);

		given(postRepository.findById(postId)).willReturn(Optional.empty());
		// when
		// then
		assertThrows(CustomException.class, () -> postService.updatePost(postId, requestDto, userDetails));
	}

	@Test
	@DisplayName("게시물 수정 실패 - 권한 없는 사용자")
	void updatePost_FAIL_UnauthorizedUser() {
		// given
		UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
		Users user = mock(Users.class);
		Users postUser = mock(Users.class);  // 게시물 작성자 mock 객체
		Post post = mock(Post.class);

		UpdatePostRequestDto requestDto = new UpdatePostRequestDto("수정된 제목", "수정된 내용");
		Long postId = 1L;

		given(user.getUserId()).willReturn(2L);
		given(userDetails.getUser()).willReturn(user);
		given(postRepository.findById(postId)).willReturn(Optional.of(post));
		given(postUser.getUserId()).willReturn(1L);
		given(post.getUsers()).willReturn(postUser);

		// when & then: 권한 없는 사용자가 수정하려고 하면 CustomException이 발생해야 함
		CustomException thrown = assertThrows(CustomException.class,
			() -> postService.updatePost(postId, requestDto, userDetails));

		// then: 예외 메시지나 상태 코드 확인
		assertEquals(StatusCode.POST_EDIT_FORBIDDEN, thrown.getStatusCode());

		// verify: findById가 한 번 호출되었는지 확인
		verify(postRepository, times(1)).findById(postId);
	}
}