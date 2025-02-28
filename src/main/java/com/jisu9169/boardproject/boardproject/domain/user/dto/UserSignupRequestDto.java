package com.jisu9169.boardproject.boardproject.domain.user.dto;

import lombok.Getter;

@Getter
public class UserSignupRequestDto {

	private String username;
	private String password;
	private String checkPassword;

}
