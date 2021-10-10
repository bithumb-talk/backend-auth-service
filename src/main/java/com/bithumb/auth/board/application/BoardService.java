package com.bithumb.auth.board.application;

import com.bithumb.auth.board.api.dto.CancleLikeContentRequest;
import com.bithumb.auth.board.api.dto.CheckLikeContentRequest;
import com.bithumb.auth.board.api.dto.LikeContentResponse;
import com.bithumb.auth.board.api.dto.FindUserLikeContentRequest;
import com.bithumb.auth.board.api.dto.FindUserLikeContentResponse;

public interface BoardService {

	void checkLikeBoardContent(CheckLikeContentRequest checkLikeContentRequest);
	void cancleLikeBoardContent(CancleLikeContentRequest cancleLikeContentRequest);
	FindUserLikeContentResponse findUserLikeBoardContent(FindUserLikeContentRequest findUserLikeContentRequest);
	LikeContentResponse checkBoardMatching(CheckLikeContentRequest checkLikeContentRequest);

	void checkLikeCommentContent(CheckLikeContentRequest checkLikeContentRequest);
	void cancleLikeCommentContent(CancleLikeContentRequest cancleLikeContentRequest);
	FindUserLikeContentResponse findUserLikeCommentContent(FindUserLikeContentRequest findUserLikeContentRequest);
	LikeContentResponse checkCommnetMatching(CheckLikeContentRequest checkLikeContentRequest);
}