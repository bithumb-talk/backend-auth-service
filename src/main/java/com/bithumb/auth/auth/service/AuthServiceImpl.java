package com.bithumb.auth.auth.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bithumb.auth.auth.controller.dto.TokenDto;
import com.bithumb.auth.auth.controller.dto.TokenRequestDto;
import com.bithumb.auth.auth.controller.dto.TokenResponseDto;
import com.bithumb.auth.auth.controller.dto.UserLoginTarget;
import com.bithumb.auth.auth.controller.dto.UserSignUpTarget;
import com.bithumb.auth.auth.entity.RefreshToken;
import com.bithumb.auth.auth.repository.RefreshTokenRepository;
import com.bithumb.auth.common.response.ErrorCode;
import com.bithumb.auth.security.jwt.TokenProvider;
import com.bithumb.auth.user.entity.User;
import com.bithumb.auth.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService{
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void signup(UserSignUpTarget userSignUpTarget) {

        if (userRepository.existsByUserId(userSignUpTarget.getUserId())) {
            throw new DuplicateKeyException(ErrorCode.ID_ALREADY_EXIST.getMessage());
        }
        if (userRepository.existsByNickname(userSignUpTarget.getNickname())) {
            throw new DuplicateKeyException(ErrorCode.NICKNAME_ALREADY_EXIST.getMessage());
        }

        User user = userSignUpTarget.toEntity(passwordEncoder);
        userRepository.save(user);
    }

    @Override
    public TokenResponseDto login(UserLoginTarget userLoginTarget) {

        //User dtoUser = userLoginTarget.toEntity(passwordEncoder);
        User user = userRepository.findByUserId(userLoginTarget.getUserId())
            .orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));

        UsernamePasswordAuthenticationToken authenticationToken = userLoginTarget.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshKey(authentication.getName())
                .refreshValue(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        return TokenResponseDto.of(user.getId(),tokenDto);
    }

    @Override
    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {

        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException(ErrorCode.REFRESH_TOKEN_IS_NOT_VALID.getMessage());
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
            .orElseThrow(() -> new RuntimeException(ErrorCode.ALREADY_LOGOUT.getMessage()));

        if (!refreshToken.getRefreshValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException(ErrorCode.USER_INFO_NOT_MATCH.getMessage());
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);
        long id = Long.parseLong(authentication.getName());

        return TokenResponseDto.of(id,tokenDto);
    }


}
