package com.jisu9169.boardproject.boardproject.domain.user;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jisu9169.boardproject.boardproject.domain.user.dto.UserSignupRequestDto;
import com.jisu9169.boardproject.boardproject.domain.user.entity.Users;
import com.jisu9169.boardproject.boardproject.global.exception.CustomException;
import com.jisu9169.boardproject.boardproject.global.exception.StatusCode;
import com.jisu9169.boardproject.boardproject.global.security.UserDetailsImpl;
import com.jisu9169.boardproject.boardproject.global.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private HttpServletRequest request;

	@Test
	@DisplayName("회원가입 성공")
	void signup_success() throws Exception {
	    // given
		UserSignupRequestDto requestDto = new UserSignupRequestDto("testUser", "password1234", "password1234");

		given(userRepository.existsByUsername(requestDto.getUsername())).willReturn(false);
		given(passwordEncoder.encode(requestDto.getPassword())).willReturn("encodedPassword");
		// when
		userService.signup(requestDto);
		// then
		verify(userRepository, times(1)).save(any(Users.class));
	}


	@Test
	@DisplayName("회원가입 실패 - 중복된 유저네임 ")
	void fail_duplicate_username() throws Exception{
	    // given
		UserSignupRequestDto requestDto = new UserSignupRequestDto("testUser", "password1234", "password1234");
		given(userRepository.existsByUsername(requestDto.getUsername())).willReturn(true);
	    // when
		// then
		assertThrows(CustomException.class, () -> userService.signup(requestDto));
	}

	@Test
	@DisplayName("회원가입 실패 - 비밀번호 불일치 ")
	void fail_password_mismatch() throws Exception{
		// given
		UserSignupRequestDto requestDto = new UserSignupRequestDto("testUser", "password1234", "notPassword1234");
		// when
		// then
		assertThrows(CustomException.class, () -> userService.signup(requestDto));
	}

	@Test
	@DisplayName("로그아웃 성공")
	void logout_success() {
		// given
		String token = "validToken";
		when(jwtUtil.getJwtFromHeader(request)).thenReturn(token);
		when(jwtUtil.validateToken(token)).thenReturn(true);

		UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

		// when
		userService.logout(userDetails);

		// then
		assertNull(SecurityContextHolder.getContext().getAuthentication(), "로그아웃 후 SecurityContext가 비어 있어야 한다.");
	}

	@Test
	@DisplayName("유효하지 않은 토큰으로 로그아웃 실패")
	void logout_invalid_token() {
		// given
		String token = "invalidToken";
		when(jwtUtil.getJwtFromHeader(request)).thenReturn(token);
		when(jwtUtil.validateToken(token)).thenReturn(false);

		UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

		// when & then
		CustomException exception = assertThrows(CustomException.class, () -> {
			userService.logout(userDetails);
		});

		assertEquals(StatusCode.INVALID_TOKEN, exception.getStatusCode());
	}


}