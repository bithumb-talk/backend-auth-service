package com.bithumb.auth.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bithumb.auth.board.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("SELECT"
		+ " CASE "
		+ "WHEN COUNT(b) > 0 "
		+ "THEN true "
		+ "ELSE false END "
		+ "FROM Comment b WHERE b.userId = :userId and b.commnetId = :commnetId")
	boolean checkAlreadyExist(@Param("userId") Long userId ,@Param("commnetId") Long commnetId);

	@Query ("select b from Comment b WHERE b.userId = :userId and b.commnetId = :commnetId")
	Optional<Comment> findTableNoByUserIdAndBoardId(@Param("userId") Long userId ,@Param("commnetId") Long commnetId);

	@Query ("select b from Comment b WHERE b.userId = :userId")
	List<Comment> findAllByUserId(@Param("userId")long userId);

}
