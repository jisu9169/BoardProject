package com.jisu9169.boardproject.boardproject.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequestDto {

	private String username;
	private String password;
	private String checkPassword;
}
