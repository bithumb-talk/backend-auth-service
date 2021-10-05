package com.bithumb.auth.board.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class findUserLikeContentResponse {

	private long userId;
	private List<Long> contentIdList;

	public static findUserLikeContentResponse of(long userId , List<Long> contentIdList) {
		return new findUserLikeContentResponse(userId, contentIdList);
	}
}
