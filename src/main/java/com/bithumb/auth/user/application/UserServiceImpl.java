package com.bithumb.auth.user.application;

import java.io.IOException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bithumb.auth.auth.repository.RefreshTokenRepository;
import com.bithumb.auth.common.response.ErrorCode;
import com.bithumb.auth.security.authentication.AuthInfo;
import com.bithumb.auth.user.api.dto.DeleteUserTarget;
import com.bithumb.auth.user.api.dto.ModifyNicknameTarget;
import com.bithumb.auth.user.api.dto.ModifyPasswordTarget;
import com.bithumb.auth.user.api.dto.UserResponseDto;
import com.bithumb.auth.user.domain.User;
import com.bithumb.auth.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RefreshTokenRepository refreshTokenRepository;
	private final S3Uploader s3Uploader;

	@Override
	public UserResponseDto changePassword(ModifyPasswordTarget target, AuthInfo authInfo) {
		validUser(target.getId(), authInfo.getId());
		User user = findUserById(target.getId());
		user.changePassword(passwordEncoder.encode(target.getPassword()));

		return UserResponseDto.of(userRepository.save(user));
	}

	@Override
	public UserResponseDto changeNickname(ModifyNicknameTarget target, AuthInfo authInfo) {
		validUser(target.getId(), authInfo.getId());
		User user = findUserById(target.getId());
		user.changeNickname(target.getNickname());
		return UserResponseDto.of(userRepository.save(user));
	}

	@Override
	public void deleteUser(DeleteUserTarget target, AuthInfo authInfo) {
		User user = findUserById(authInfo.getId());

		validUser(target.getId(), authInfo.getId());
		validPassword(target.getPassword(),user.getPassword());

		userRepository.deleteById(target.getId());
		refreshTokenRepository.deleteById(String.valueOf(target.getId()));
	}

	@Override
	public void saveProfileImg(long userId, MultipartFile multipartFile) throws IOException {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));

		String userImg = s3Uploader.upload(multipartFile, user.getId());
		userRepository.saveUserProfileImg(userId,userImg);

		System.out.println(userImg);
	}

	private User findUserById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
	}

	private void validUser(long requestedUserId, long AuthedUserId) {
		if (requestedUserId != AuthedUserId) {
			throw new SecurityException(ErrorCode.ID_NOT_MATCH.getMessage());
		}
	}

	private void validPassword(String requestPassword, String encodedPassword) {
		if (!passwordEncoder.matches(requestPassword, encodedPassword)) {
			throw new SecurityException(ErrorCode.PASSWORD_NOT_MATCH.getMessage());
		}

	}
}
