package com.jisu9169.boardproject.boardproject.domain.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jisu9169.boardproject.boardproject.domain.user.dto.UserSignupRequestDto;
import com.jisu9169.boardproject.boardproject.global.dto.MessageResponseDto;
import com.jisu9169.boardproject.boardproject.global.exception.StatusCode;
import com.jisu9169.boardproject.boardproject.global.response.ResponseFactory;
import com.jisu9169.boardproject.boardproject.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController("/api")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;


	@PostMapping("/users/signup")
	public ResponseEntity<MessageResponseDto> signup(final @RequestBody UserSignupRequestDto requestDto) {
		userService.signup(requestDto);
		return ResponseFactory.created(StatusCode.SUCCESS_SIGNUP);
	}

	@PostMapping("/users/logout")
	public ResponseEntity<MessageResponseDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		userService.logout(userDetails);

		return ResponseFactory.ok(StatusCode.SUCESS_LOGOUT);

	}
}
