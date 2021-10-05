package com.bithumb.auth.board.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeContentResponse {
	String likeStatus;

	public static LikeContentResponse of(String likeStatus) {
		return new LikeContentResponse(likeStatus);
	}
}
