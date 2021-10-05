package com.bithumb.auth.user.application;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.bithumb.auth.security.authentication.AuthInfo;
import com.bithumb.auth.user.api.dto.DeleteUserTarget;
import com.bithumb.auth.user.api.dto.FindUserInfoResponse;
import com.bithumb.auth.user.api.dto.ModifyNicknameTarget;
import com.bithumb.auth.user.api.dto.ModifyPasswordTarget;
import com.bithumb.auth.user.api.dto.ReSaveDeviceTokenTarget;
import com.bithumb.auth.user.api.dto.UserApiResponse;
import com.bithumb.auth.user.api.dto.UserResponseDto;

public interface UserService {

	UserResponseDto changePassword(ModifyPasswordTarget target, AuthInfo authInfo);
	UserResponseDto changeNickname(ModifyNicknameTarget target, AuthInfo authInfo);
	void deleteUser(DeleteUserTarget target, AuthInfo authInfo);
	void saveProfileImg(long userId, MultipartFile multipartFile) throws IOException;
	UserApiResponse saveDeviceToken(ReSaveDeviceTokenTarget reSaveDeviceTokenTarget);
	FindUserInfoResponse getMyInfo(long userId, AuthInfo aUthInfo);


}
