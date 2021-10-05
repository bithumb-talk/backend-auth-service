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
public class CheckLikeContentRequest {
	private long userId;
	private long contentId;
	private AuthInfo authInfo;

	public static CheckLikeContentRequest toParam(long userId, long contentId, AuthInfo authInfo) {
		return new CheckLikeContentRequest(userId, contentId, authInfo);
	}

}
