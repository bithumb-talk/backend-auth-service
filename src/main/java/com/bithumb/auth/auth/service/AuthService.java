package com.bithumb.auth.auth.service;

import com.bithumb.auth.auth.controller.dto.TokenRequestDto;
import com.bithumb.auth.auth.controller.dto.TokenResponseDto;
import com.bithumb.auth.auth.controller.dto.UserLoginTarget;
import com.bithumb.auth.auth.controller.dto.UserSignUpTarget;

public interface AuthService {
  void signup(UserSignUpTarget userSignUpTarget);
  TokenResponseDto login(UserLoginTarget userLoginTarget);
  TokenResponseDto reissue(TokenRequestDto tokenRequestDto);
  boolean checkDuplicateUserId(String userId);
  boolean checkDuplicateNickname(String nickname);
}
