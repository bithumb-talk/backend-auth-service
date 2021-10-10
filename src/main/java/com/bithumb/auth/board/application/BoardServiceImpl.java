package com.bithumb.auth.board.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bithumb.auth.board.api.dto.CancleLikeContentRequest;
import com.bithumb.auth.board.api.dto.CheckLikeContentRequest;
import com.bithumb.auth.board.api.dto.LikeContentResponse;
import com.bithumb.auth.board.api.dto.FindUserLikeContentRequest;
import com.bithumb.auth.board.api.dto.FindUserLikeContentResponse;
import com.bithumb.auth.board.entity.Board;
import com.bithumb.auth.board.entity.Comment;
import com.bithumb.auth.board.repository.BoardRepository;
import com.bithumb.auth.board.repository.CommentRepository;
import com.bithumb.auth.common.response.ErrorCode;
import com.bithumb.auth.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;

	@Override
	public void checkLikeBoardContent(CheckLikeContentRequest dto) {

		userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
		validUser(dto.getUserId(), dto.getAuthInfo().getId());

		Boolean checkBoard = boardRepository.checkAlreadyExist(dto.getUserId(), dto.getContentId());

		if (checkBoard == true) {
			throw new IllegalArgumentException(ErrorCode.LIKE_BOARD_ALREADY_EXIST.getMessage());
		}

		Board board = Board.builder()
			.userId(dto.getUserId())
			.boardId(dto.getContentId())
			.build();

		boardRepository.save(board);
	}

	@Override
	public void cancleLikeBoardContent(CancleLikeContentRequest dto) {
		userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
		validUser(dto.getUserId(), dto.getAuthInfo().getId());

		Board board = boardRepository.findTableNoByUserIdAndBoardId(dto.getUserId(), dto.getContentId())
			.orElseThrow(() -> new NullPointerException(ErrorCode.LIKE_BOARD_NOT_EXIST.getMessage()));

		boardRepository.delete(board);
	}

	@Override
	public FindUserLikeContentResponse findUserLikeBoardContent(FindUserLikeContentRequest dto) {
		userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
		validUser(dto.getUserId(), dto.getAuthInfo().getId());

		List<Board> boardList = boardRepository.findAllByUserId(dto.getUserId());

		System.out.println(boardList);
		List<Long> onlyBoardId = boardList.stream().map(Board::getBoardId).collect(Collectors.toList());

		return FindUserLikeContentResponse.of(dto.getUserId(), onlyBoardId);
	}

	@Override
	public LikeContentResponse checkBoardMatching(CheckLikeContentRequest dto) {
		userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
		validUser(dto.getUserId(), dto.getAuthInfo().getId());

		Boolean checkComment = boardRepository.checkAlreadyExist(dto.getUserId(), dto.getContentId());

		if (checkComment == false) {
			return LikeContentResponse.of("false");
		}

		return LikeContentResponse.of("true");
	}

	@Override
	public void checkLikeCommentContent(CheckLikeContentRequest dto) {
		userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
		validUser(dto.getUserId(), dto.getAuthInfo().getId());

		Boolean checkComment = commentRepository.checkAlreadyExist(dto.getUserId(), dto.getContentId());

		if (checkComment == true) {
			throw new IllegalArgumentException(ErrorCode.LIKE_COMMENT_ALREADY_EXIST.getMessage());
		}

		Comment comment = Comment.builder()
			.userId(dto.getUserId())
			.commnetId(dto.getContentId())
			.build();

		commentRepository.save(comment);
	}

	@Override
	public void cancleLikeCommentContent(CancleLikeContentRequest dto) {
		userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
		validUser(dto.getUserId(), dto.getAuthInfo().getId());

		Comment comment = commentRepository.findTableNoByUserIdAndBoardId(dto.getUserId(), dto.getContentId())
			.orElseThrow(() -> new NullPointerException(ErrorCode.LIKE_COMMENT_NOT_EXIST.getMessage()));

		commentRepository.delete(comment);
	}

	@Override
	public FindUserLikeContentResponse findUserLikeCommentContent(FindUserLikeContentRequest dto) {
		userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
		validUser(dto.getUserId(), dto.getAuthInfo().getId());

		List<Comment> commentList = commentRepository.findAllByUserId(dto.getUserId());

		List<Long> onlyCommmentId = commentList.stream().map(Comment::getCommnetId).collect(Collectors.toList());

		return FindUserLikeContentResponse.of(dto.getUserId(), onlyCommmentId);
	}

	@Override
	public LikeContentResponse checkCommnetMatching(CheckLikeContentRequest dto) {
		userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
		validUser(dto.getUserId(), dto.getAuthInfo().getId());

		Boolean checkComment = commentRepository.checkAlreadyExist(dto.getUserId(), dto.getContentId());

		if (checkComment == false) {
			return LikeContentResponse.of("false");
		}

		return LikeContentResponse.of("true");
	}

	private void validUser(long requestedUserId, long AuthedUserId) {
		if (requestedUserId != AuthedUserId) {
			throw new IllegalArgumentException(ErrorCode.ID_NOT_MATCH.getMessage());
		}
	}
}
