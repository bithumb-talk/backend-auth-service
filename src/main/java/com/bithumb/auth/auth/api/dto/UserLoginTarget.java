package com.bithumb.auth.auth.api.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bithumb.auth.user.domain.Authority;
import com.bithumb.auth.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserLoginTarget {

    private String userId;
    private String password;
    private String deviceToken;


    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
            .userId(userId)
            .password(passwordEncoder.encode(password))
            .authority(Authority.ROLE_USER)
            .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userId, password);
    }
}