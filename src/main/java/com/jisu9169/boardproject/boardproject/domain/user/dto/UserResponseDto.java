package com.jisu9169.boardproject.boardproject.domain.user.dto;

import com.jisu9169.boardproject.boardproject.domain.user.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserResponseDto {

	private Long userId;
	private String username;

	public static UserResponseDto from(Users user) {
		return UserResponseDto.builder()
			.userId(user.getUserId())
			.username(user.getUsername())
			.build();
	}
}
