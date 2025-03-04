package com.jisu9169.boardproject.boardproject.global.response;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jisu9169.boardproject.boardproject.global.dto.MessageResponseDto;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityResponse {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public void sendResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {

		MessageResponseDto responseDto = new MessageResponseDto(status, message);
		String json = objectMapper.writeValueAsString(responseDto);

		response.setStatus(status.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);

	}
}
