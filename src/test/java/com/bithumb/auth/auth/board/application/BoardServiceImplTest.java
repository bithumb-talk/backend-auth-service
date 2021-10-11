package com.bithumb.auth.auth.board.application;

import static com.bithumb.auth.user.domain.Authority.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bithumb.auth.board.api.dto.CancleLikeContentRequest;
import com.bithumb.auth.board.api.dto.CheckLikeContentRequest;
import com.bithumb.auth.board.api.dto.FindUserLikeContentRequest;
import com.bithumb.auth.board.api.dto.FindUserLikeContentResponse;
import com.bithumb.auth.board.api.dto.LikeContentResponse;
import com.bithumb.auth.board.application.BoardServiceImpl;
import com.bithumb.auth.board.entity.Board;
import com.bithumb.auth.board.entity.Comment;
import com.bithumb.auth.board.repository.BoardRepository;
import com.bithumb.auth.board.repository.CommentRepository;
import com.bithumb.auth.security.authentication.AuthInfo;
import com.bithumb.auth.user.domain.User;
import com.bithumb.auth.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

	//@InjectMocks
	BoardServiceImpl boardService;
	@Mock
	BoardRepository boardRepository;
	@Mock
	CommentRepository commnetRepository;
	@Mock
	UserRepository userRepository;

	@BeforeEach
	void setUp() {
		this.boardService = new BoardServiceImpl(boardRepository, commnetRepository, userRepository);
	}

	//dto
	final User user = User.builder()
		.id(1l)
		.userId("bithumb")
		.password("root")
		.nickname("nickname")
		.authority(ROLE_USER)
		.build();

	//dto
	final AuthInfo authInfo = AuthInfo.UserOf(1l);

	//dto
	final CheckLikeContentRequest checkLikeContentRequest = CheckLikeContentRequest.builder()
		.userId(1l)
		.contentId(2l)
		.authInfo(authInfo)
		.build();


	//dto
	final Board board = Board.builder()
		.tableNo(1l)
		.userId(1l)
		.boardId(2l)
		.build();

	@Test
	void checkLikeBoardContent() {
		// given
		given(userRepository.findById(any())).willReturn(java.util.Optional.ofNullable(user));
		given(boardRepository.checkAlreadyExist(any(), any())).willReturn(false);

		// when
		boardService.checkLikeBoardContent(checkLikeContentRequest);

		//then
		then(boardRepository).should(times(1)).save(any());
	}

	@Test
	void cancleLikeBoardContent() {

		// dto
		CancleLikeContentRequest dto = CancleLikeContentRequest.builder()
			.userId(1l)
			.contentId(2l)
			.authInfo(authInfo)
			.build();

		// given
		given(userRepository.findById(any())).willReturn(java.util.Optional.ofNullable(user));
		given(boardRepository.findTableNoByUserIdAndBoardId(any(),any())).willReturn(
			java.util.Optional.ofNullable(board));

		// when
		boardService.cancleLikeBoardContent(dto);

		//then
		then(boardRepository).should(times(1)).delete(any());
		//assertThat(findUserLikeContentResponse, is(findUserLikeContentResponsedto));
	}

	@Test
	void findUserLikeBoardContent() {
		//dto
		final FindUserLikeContentRequest findUserLikeContentRequest = FindUserLikeContentRequest.builder()
			.userId(1l)
			.authInfo(authInfo)
			.build();

		//dto
		final List<Board> boardList = List.of(
			new Board(1l, 1l, 2l),
			new Board(2l, 1l, 3l),
			new Board(3l, 1l, 7l),
			new Board(4l, 1l, 8l),
			new Board(5l, 1l, 13l));

		//dto
		final List<Long> boardLongList = List.of(
			2l,3l,7l,8l,13l
		);

		//dto
		final FindUserLikeContentResponse findUserLikeContentResponsedto = FindUserLikeContentResponse.builder()
			.userId(1l)
			.contentIdList(boardLongList)
			.build();

		// given
		given(userRepository.findById(any())).willReturn(java.util.Optional.ofNullable(user));
		given(boardRepository.findAllByUserId(1l)).willReturn(boardList);

		// when
		FindUserLikeContentResponse findUserLikeContentResponse
			= boardService.findUserLikeBoardContent(findUserLikeContentRequest);

		//then
		assertThat(findUserLikeContentResponse, is(findUserLikeContentResponsedto));
	}

	@Test
	void checkBoardMatching() {
		//dto
		CheckLikeContentRequest dto = CheckLikeContentRequest.builder()
			.userId(1l)
			.authInfo(authInfo)
			.build();

		//dto
		LikeContentResponse responseDto = LikeContentResponse.of("true");

		//given
		given(userRepository.findById(any())).willReturn(java.util.Optional.ofNullable(user));
		given(boardRepository.checkAlreadyExist(any(),any())).willReturn(true);

		//when
		LikeContentResponse response = boardService.checkBoardMatching(dto);

		//then
		assertThat(response, is(responseDto));
	}

	@Test
	void checkLikeCommentContent() {
		// given
		given(userRepository.findById(any())).willReturn(java.util.Optional.ofNullable(user));
		given(commnetRepository.checkAlreadyExist(any(), any())).willReturn(false);

		// when
		boardService.checkLikeCommentContent(checkLikeContentRequest);

		//then
		then(commnetRepository).should(times(1)).save(any());
	}

	@Test
	void cancleLikeCommentContent() {
		// dto
		CancleLikeContentRequest dto = CancleLikeContentRequest.builder()
			.userId(1l)
			.contentId(2l)
			.authInfo(authInfo)
			.build();

		Comment comment = Comment.builder()
			.tableNo(1l)
			.userId(1l)
			.commnetId(3l)
			.build();

		// given
		given(userRepository.findById(any())).willReturn(java.util.Optional.ofNullable(user));
		given(commnetRepository.findTableNoByUserIdAndBoardId(any(),any())).willReturn(
			java.util.Optional.ofNullable(comment));

		// when
		boardService.cancleLikeCommentContent(dto);

		//then
		then(commnetRepository).should(times(1)).delete(any());
		//assertThat(findUserLikeContentResponse, is(findUserLikeContentResponsedto));
	}

	@Test
	void findUserLikeCommentContent() {
		//dto
		final FindUserLikeContentRequest findUserLikeContentRequest = FindUserLikeContentRequest.builder()
			.userId(1l)
			.authInfo(authInfo)
			.build();

		//dto
		final List<Comment> commentList = List.of(
			new Comment(1l, 1l, 2l),
			new Comment(2l, 1l, 3l),
			new Comment(3l, 1l, 7l),
			new Comment(4l, 1l, 8l),
			new Comment(5l, 1l, 13l));

		//dto
		final List<Long> boardLongList = List.of(
			2l,3l,7l,8l,13l
		);

		//dto
		final FindUserLikeContentResponse findUserLikeContentResponsedto = FindUserLikeContentResponse.builder()
			.userId(1l)
			.contentIdList(boardLongList)
			.build();

		// given
		given(userRepository.findById(any())).willReturn(java.util.Optional.ofNullable(user));
		given(commnetRepository.findAllByUserId(1l)).willReturn(commentList);

		// when
		FindUserLikeContentResponse findUserLikeContentResponse
			= boardService.findUserLikeCommentContent(findUserLikeContentRequest);

		//then
		assertThat(findUserLikeContentResponse, is(findUserLikeContentResponsedto));
	}

	@Test
	void checkCommnetMatching() {
		//dto
		CheckLikeContentRequest dto = CheckLikeContentRequest.builder()
			.userId(1l)
			.authInfo(authInfo)
			.build();

		//dto
		LikeContentResponse responseDto = LikeContentResponse.of("true");

		//given
		given(userRepository.findById(any())).willReturn(java.util.Optional.ofNullable(user));
		given(commnetRepository.checkAlreadyExist(any(),any())).willReturn(true);

		//when
		LikeContentResponse response = boardService.checkCommnetMatching(dto);

		//then
		assertThat(response, is(responseDto));

	}
}