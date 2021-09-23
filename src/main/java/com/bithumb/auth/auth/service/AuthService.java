package com.bithumb.auth.auth.service;

import com.bithumb.auth.auth.controller.dto.TokenResponseDto;
import com.bithumb.auth.auth.controller.dto.UserLoginTarget;

public interface AuthService {

  TokenResponseDto login(UserLoginTarget userLoginTarget);
}
