package com.bithumb.auth.board.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class FindUserLikeContentResponse {

	private long userId;
	private List<Long> contentIdList;

	public static FindUserLikeContentResponse of(long userId , List<Long> contentIdList) {
		return new FindUserLikeContentResponse(userId, contentIdList);
	}
}
