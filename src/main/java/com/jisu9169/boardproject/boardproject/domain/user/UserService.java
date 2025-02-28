package com.jisu9169.boardproject.boardproject.domain.user;

import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jisu9169.boardproject.boardproject.domain.user.dto.UserSignupRequestDto;
import com.jisu9169.boardproject.boardproject.domain.user.entity.Users;
import com.jisu9169.boardproject.boardproject.global.exception.CustomException;
import com.jisu9169.boardproject.boardproject.global.exception.StatusCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void signup(UserSignupRequestDto requestDto) {
		if(userRepository.existsByUsername(requestDto.getUsername())) {
			throw new CustomException(StatusCode.ALREADY_EXIST_USER);
		}
		if(!Objects.equals(requestDto.getPassword(), requestDto.getCheckPassword())) {
			throw new CustomException(StatusCode.CHECK_PASSWORD);
		}
		Users user = Users.createUser(requestDto, passwordEncoder);
		userRepository.save(user);
	}
}
