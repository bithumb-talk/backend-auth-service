package com.bithumb.auth.auth.api.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest {

    @NotBlank(message = "Input Your ID")
    private String userId;
    @NotBlank(message = "Input Your Password")
    private String password;
    @NotBlank(message = "Input Your deviceToken")
    private String deviceToken;

    public UserLoginTarget toParam() {
        return UserLoginTarget.builder()
            .userId(userId)
            .password(password)
            .deviceToken(deviceToken)
            .build();
    }
}
