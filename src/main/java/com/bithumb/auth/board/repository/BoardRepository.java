package com.bithumb.auth.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bithumb.auth.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query("SELECT"
		+ " CASE "
		+ "WHEN COUNT(b) > 0 "
		+ "THEN true "
		+ "ELSE false END "
		+ "FROM Board b WHERE b.userId = :userId and b.boardId = :boardId")
	boolean checkAlreadyExist(@Param("userId") Long userId ,@Param("boardId") Long boardId);

	@Query ("select b from Board b WHERE b.userId = :userId and b.boardId = :boardId")
	Optional<Board> findTableNoByUserIdAndBoardId(@Param("userId") Long userId ,@Param("boardId") Long boardId);

	@Query ("select b from Board b WHERE b.userId = :userId")
	List<Board> findAllByUserId(@Param("userId")long userId);
}
