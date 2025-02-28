package com.jisu9169.boardproject.boardproject.domain.user;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jisu9169.boardproject.boardproject.domain.user.dto.UserSignupRequestDto;
import com.jisu9169.boardproject.boardproject.domain.user.entity.Users;
import com.jisu9169.boardproject.boardproject.global.exception.CustomException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;

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
}