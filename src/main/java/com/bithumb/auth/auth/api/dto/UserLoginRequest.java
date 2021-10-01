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
    //@Range(min = 5, max = 15, message = "Input Correct Password")
    private String password;


    public UserLoginTarget toParam() {
        return UserLoginTarget.builder()
                .userId(userId)
                .password(password)
                .build();
    }


}
