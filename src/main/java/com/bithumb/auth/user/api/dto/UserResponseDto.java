package com.bithumb.auth.user.api.dto;

import com.bithumb.auth.user.domain.Authority;
import com.bithumb.auth.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private long id;
    private String userId;
    private String nickname;
    private Authority authority;

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getId(), user.getUserId(), user.getNickname(), user.getAuthority());
    }
}
