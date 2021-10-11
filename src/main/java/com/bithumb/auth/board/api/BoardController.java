package com.bithumb.auth.board.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bithumb.auth.board.api.dto.CancleLikeContentRequest;
import com.bithumb.auth.board.api.dto.CheckLikeContentRequest;
import com.bithumb.auth.board.api.dto.LikeContentResponse;
import com.bithumb.auth.board.api.dto.FindUserLikeContentRequest;
import com.bithumb.auth.board.api.dto.FindUserLikeContentResponse;
import com.bithumb.auth.board.application.BoardService;
import com.bithumb.auth.common.response.ApiResponse;
import com.bithumb.auth.common.response.StatusCode;
import com.bithumb.auth.common.response.SuccessCode;
import com.bithumb.auth.security.authentication.AuthInfo;
import com.bithumb.auth.security.authentication.AuthRequired;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-boards")
@Slf4j
public class BoardController {

	private final BoardService boardService;
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

	@AuthRequired
	@PostMapping("/{user-id}/like-board-content/{board-id}")
	public ResponseEntity<?> checkLikeBoardContent(@PathVariable("user-id") long userId, @PathVariable("board-id") long boardId, AuthInfo authInfo) {
		LOGGER.info("좋아요 누른 게시글 넘버 {}.",boardId);
		CheckLikeContentRequest dto = CheckLikeContentRequest.toParam(userId,boardId,authInfo);
		boardService.checkLikeBoardContent(dto);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.LIKE_BOARD_SAVE_SUCCESS.getMessage(), LikeContentResponse.of("true"));
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@AuthRequired
	@DeleteMapping("/{user-id}/like-board-content/{board-id}")
	public ResponseEntity<?> cancleLikeBoardContent(@PathVariable("user-id") long userId, @PathVariable("board-id") long boardId, AuthInfo authInfo) {
		CancleLikeContentRequest dto = CancleLikeContentRequest.toParam(userId,boardId,authInfo);
		boardService.cancleLikeBoardContent(dto);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.LIKE_BOARD_CANCLE_SUCCESS.getMessage(), LikeContentResponse.of("false"));
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@AuthRequired
	@GetMapping("/{user-id}/like-board-contents")
	public ResponseEntity<?> findLikeBoardContentByUserId(@PathVariable("user-id") long userId, AuthInfo authInfo) {
		FindUserLikeContentRequest dto = FindUserLikeContentRequest.toParam(userId,authInfo);
		FindUserLikeContentResponse boardList = boardService.findUserLikeBoardContent(dto);

		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.FIND_LIKE_BOARD_LIST_SUCCESS.getMessage(),boardList);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@AuthRequired
	@GetMapping("/{user-id}/like-board-content/{board-id}")
	public ResponseEntity<?> matchingBoardLikeContent(@PathVariable("user-id") long userId, @PathVariable("board-id") long boardId, AuthInfo authInfo) {
		CheckLikeContentRequest dto = CheckLikeContentRequest.toParam(userId,boardId,authInfo);
		LikeContentResponse response = boardService.checkBoardMatching(dto);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.CHECK_LIKE_BOARD_SUCCESS.getMessage(),response);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@AuthRequired
	@PostMapping("/{user-id}/like-comment-content/{comment-id}")
	public ResponseEntity<?> checkLikeCommentContent(@PathVariable("user-id") long userId, @PathVariable("comment-id") long commnetId, AuthInfo authInfo) {
		CheckLikeContentRequest dto = CheckLikeContentRequest.toParam(userId,commnetId,authInfo);
		boardService.checkLikeCommentContent(dto);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.LIKE_COMMENT_SAVE_SUCCESS.getMessage(),LikeContentResponse.of("true"));
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@AuthRequired
	@DeleteMapping("/{user-id}/like-comment-content/{comment-id}")
	public ResponseEntity<?> cancleLikeCommentContent(@PathVariable("user-id") long userId, @PathVariable("comment-id") long commnetId, AuthInfo authInfo) {
		CancleLikeContentRequest dto = CancleLikeContentRequest.toParam(userId,commnetId,authInfo);
		boardService.cancleLikeCommentContent(dto);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.LIKE_COMMENT_CANCLE_SUCCESS.getMessage(),LikeContentResponse.of("false"));
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@AuthRequired
	@GetMapping("/{user-id}/like-comment-contents")
	public ResponseEntity<?> findLikeCommentContentByUserId(@PathVariable("user-id") long userId, AuthInfo authInfo) {
		FindUserLikeContentRequest dto = FindUserLikeContentRequest.toParam(userId,authInfo);
		FindUserLikeContentResponse boardList = boardService.findUserLikeCommentContent(dto);

		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.FIND_LIKE_COMMENT_LIST_SUCCESS.getMessage(),boardList);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}


	@AuthRequired
	@GetMapping("/{user-id}/like-comment-content/{comment-id}")
	public ResponseEntity<?> matchingCommentLikeContent(@PathVariable("user-id") long userId, @PathVariable("comment-id") long commnetId, AuthInfo authInfo) {
		CheckLikeContentRequest dto = CheckLikeContentRequest.toParam(userId,commnetId,authInfo);
		LikeContentResponse response = boardService.checkCommnetMatching(dto);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.CHECK_LIKE_COMMENT_SUCCESS.getMessage(),response);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}


}
