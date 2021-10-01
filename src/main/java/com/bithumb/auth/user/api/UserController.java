package com.bithumb.auth.user.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bithumb.auth.common.response.ApiResponse;
import com.bithumb.auth.common.response.StatusCode;
import com.bithumb.auth.common.response.SuccessCode;
import com.bithumb.auth.security.authentication.AuthInfo;
import com.bithumb.auth.security.authentication.AuthRequired;
import com.bithumb.auth.user.api.dto.ModifyNicknameRequest;
import com.bithumb.auth.user.api.dto.ModifyPasswordRequest;
import com.bithumb.auth.user.api.dto.UserResponseDto;
import com.bithumb.auth.user.application.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@AuthRequired
	@PutMapping("/password/{id}")
	public ResponseEntity<?> modifyPassword(@PathVariable long id, @Valid @RequestBody ModifyPasswordRequest dto,
		AuthInfo authInfo) {
		UserResponseDto responseDto = userService.changePassword(dto.toParam(id), authInfo);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.USER_UPDATE_PASSWORD_SUCESS.getMessage(), responseDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@AuthRequired
	@PutMapping("/nickname/{id}")
	public ResponseEntity<?> modifyNickname(@PathVariable long id, @Valid @RequestBody ModifyNicknameRequest dto, AuthInfo authInfo) {
		UserResponseDto responseDto = userService.changeNickname(dto.toParam(id), authInfo);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.USER_UPDATE_NICKNAME_SUCCESS.getMessage(),responseDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

}