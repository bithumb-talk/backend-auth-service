package com.bithumb.auth.board.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bithumb.auth.board.api.dto.CancleLikeContentRequest;
import com.bithumb.auth.board.api.dto.CheckLikeContentRequest;
import com.bithumb.auth.board.api.dto.LikeContentResponse;
import com.bithumb.auth.board.application.BoardService;
import com.bithumb.auth.common.response.ApiResponse;
import com.bithumb.auth.common.response.StatusCode;
import com.bithumb.auth.common.response.SuccessCode;
import com.bithumb.auth.security.authentication.AuthInfo;
import com.bithumb.auth.security.authentication.AuthRequired;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-boards")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")

public class BoardController {

	private final BoardService boardService;

	@AuthRequired
	@PostMapping("/{user-id}/like-board-content/{board-id}")
	public ResponseEntity<?> checkLikeBoardContent(@PathVariable("user-id") long userId, @PathVariable("board-id") long boardId, AuthInfo authInfo) {
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




}
