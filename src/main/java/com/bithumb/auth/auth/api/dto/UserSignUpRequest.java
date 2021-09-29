package com.bithumb.auth.auth.api.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest {

	@NotBlank(message = "Input Your ID")
	private String userId;
	@NotBlank(message = "Input Your Password")
	private String password;
	@NotBlank(message = "Input Your Nickname")
	private String nickname;

	public UserSignUpTarget toParam() {
		return UserSignUpTarget.builder()
			.userId(userId)
			.password(password)
			.nickname(nickname)
			.build();
	}

	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(userId, password);
	}
}
