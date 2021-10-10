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
public class FindUserLikeContentRequest {
	private long userId;
	private AuthInfo authInfo;

	public static FindUserLikeContentRequest toParam(long userId, AuthInfo authInfo) {
		return new FindUserLikeContentRequest(userId,authInfo);
	}




}
