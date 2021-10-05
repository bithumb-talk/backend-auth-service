package com.bithumb.auth.board.api.dto;

import com.bithumb.auth.security.authentication.AuthInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class findUserLikeContentRequest {
	private long userId;
	private AuthInfo authInfo;

	public static findUserLikeContentRequest toParam(long userId, AuthInfo authInfo) {
		return new findUserLikeContentRequest(userId,authInfo);
	}




}
