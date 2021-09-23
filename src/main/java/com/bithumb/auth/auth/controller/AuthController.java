package com.bithumb.auth.auth.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bithumb.auth.auth.controller.dto.TokenResponseDto;
import com.bithumb.auth.auth.controller.dto.UserLoginRequest;
import com.bithumb.auth.auth.service.AuthService;
import com.bithumb.auth.common.response.ApiResponse;
import com.bithumb.auth.common.response.StatusCode;
import com.bithumb.auth.common.response.SuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        TokenResponseDto tokenDto = authService.login(userLoginRequest.toParam());
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
            SuccessCode.USER_LOGIN_SUCCESS.getMessage(),tokenDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
