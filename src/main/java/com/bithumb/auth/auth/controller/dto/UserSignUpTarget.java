package com.bithumb.auth.auth.controller.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.bithumb.auth.user.entity.Authority;
import com.bithumb.auth.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignUpTarget {

	private String userId;
	private String password;
	private String nickname;
	private Authority authority;

	public User toEntity(PasswordEncoder passwordEncoder) {
		return User.builder()
			.userId(userId)
			.password(passwordEncoder.encode(password))
			.nickname(nickname)
			.authority(Authority.ROLE_USER)
			.build();
	}

}
