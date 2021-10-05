package com.bithumb.auth.board.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bithumb.auth.board.api.dto.CheckLikeContentRequest;
import com.bithumb.auth.board.entity.Board;
import com.bithumb.auth.board.repository.BoardRepository;
import com.bithumb.auth.common.response.ErrorCode;
import com.bithumb.auth.user.repository.UserRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final UserRepository userRepository;

	@Override
	public void checkLikeBoardContent(CheckLikeContentRequest dto) {

		userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
		validUser(dto.getUserId(),dto.getAuthInfo().getId());

		Boolean checkBoard = boardRepository.checkAlreadyExist(dto.getUserId(), dto.getContentId());

		if (checkBoard == true){
			throw new IllegalArgumentException(ErrorCode.LIKE_BOARD_ALREADY_EXIST.getMessage());
		}

		Board board = Board.builder()
			.userId(dto.getUserId())
			.boardId(dto.getContentId())
			.build();

		boardRepository.save(board);
	}



	private void validUser(long requestedUserId , long AuthedUserId){
		if(requestedUserId != AuthedUserId){
			throw new IllegalArgumentException(ErrorCode.ID_NOT_MATCH.getMessage());
		}
	}
}
