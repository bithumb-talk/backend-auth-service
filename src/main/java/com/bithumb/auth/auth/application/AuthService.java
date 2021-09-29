package com.bithumb.auth.auth.application;

import com.bithumb.auth.auth.api.dto.TokenRequestDto;
import com.bithumb.auth.auth.api.dto.TokenResponseDto;
import com.bithumb.auth.auth.api.dto.UserLoginTarget;
import com.bithumb.auth.auth.api.dto.UserSignUpTarget;

public interface AuthService {
  void signup(UserSignUpTarget userSignUpTarget);
  TokenResponseDto login(UserLoginTarget userLoginTarget);
  TokenResponseDto reissue(TokenRequestDto tokenRequestDto);
  boolean checkDuplicateUserId(String userId);
  boolean checkDuplicateNickname(String nickname);
}
