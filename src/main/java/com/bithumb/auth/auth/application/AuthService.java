package com.bithumb.auth.auth.application;

import com.bithumb.auth.auth.api.dto.AuthApiResponse;
import com.bithumb.auth.auth.api.dto.TokenRequestDto;
import com.bithumb.auth.auth.api.dto.UserLoginTarget;
import com.bithumb.auth.auth.api.dto.UserSignUpTarget;

public interface AuthService {
  void signup(UserSignUpTarget userSignUpTarget);
  AuthApiResponse login(UserLoginTarget userLoginTarget);
  AuthApiResponse reissue(TokenRequestDto tokenRequestDto);
  boolean checkDuplicateNickname(String nickname);
  boolean checkDuplicateUserId(String userId);
}
