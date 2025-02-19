package com.jisu9169.boardproject.boardproject.global.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jisu9169.boardproject.boardproject.global.dto.ExceptionResponseDto;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ExceptionResponseDto> handleInvalidPasswordException(
		HttpServletRequest request, CustomException e) {
		ExceptionResponseDto exceptionResponse = ExceptionResponseDto.builder()
			.message(e.getMessage())
			.path(request.getRequestURI())
			.build();
		return new ResponseEntity<>(exceptionResponse,
			HttpStatusCode.valueOf(e.getStatusCode().getStatusCode().value()));
	}

	// 인증 후 유효성 검사를 하기 위한 예외 처리 설정
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ExceptionResponseDto> handleIllegalArgumentException(IllegalArgumentException e,
		HttpServletRequest request) {
		ExceptionResponseDto exceptionResponse = ExceptionResponseDto.builder()
			.message(e.getMessage())
			.path(request.getRequestURI())
			.build();
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<ExceptionResponseDto> handleIllegalArgumentException(NullPointerException e,
		HttpServletRequest request) {
		ExceptionResponseDto exceptionResponse = ExceptionResponseDto.builder()
			.message(e.getMessage())
			.path(request.getRequestURI())
			.build();
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
