package com.bithumb.auth.board.application;

import com.bithumb.auth.board.api.dto.CancleLikeContentRequest;
import com.bithumb.auth.board.api.dto.CheckLikeContentRequest;
import com.bithumb.auth.board.api.dto.LikeContentResponse;
import com.bithumb.auth.board.api.dto.findUserLikeContentRequest;
import com.bithumb.auth.board.api.dto.findUserLikeContentResponse;

public interface BoardService {

	void checkLikeBoardContent(CheckLikeContentRequest checkLikeContentRequest);
	void cancleLikeBoardContent(CancleLikeContentRequest cancleLikeContentRequest);
	findUserLikeContentResponse findUserLikeBoardContent(findUserLikeContentRequest findUserLikeContentRequest);
	LikeContentResponse checkBoardMatching(CheckLikeContentRequest checkLikeContentRequest);


}