package com.bithumb.auth.user.application;

import com.bithumb.auth.security.authentication.AuthInfo;
import com.bithumb.auth.user.api.dto.ModifyPasswordTarget;
import com.bithumb.auth.user.api.dto.UserResponseDto;

public interface UserService {

	UserResponseDto changePassword(ModifyPasswordTarget target, AuthInfo authInfo);




}
