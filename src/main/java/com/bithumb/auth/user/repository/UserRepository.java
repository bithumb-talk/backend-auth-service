package com.bithumb.auth.user.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bithumb.auth.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNickname(String nickname);
    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String userId);

}
