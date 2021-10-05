package com.bithumb.auth.board.application;

import com.bithumb.auth.board.api.dto.CancleLikeContentRequest;
import com.bithumb.auth.board.api.dto.CheckLikeContentRequest;

public interface BoardService {

	void checkLikeBoardContent(CheckLikeContentRequest checkLikeContentRequest);
	void cancleLikeBoardContent(CancleLikeContentRequest cancleLikeContentRequest);



}