package com.bithumb.auth.auth.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bithumb.auth.auth.api.dto.AuthApiResponse;
import com.bithumb.auth.auth.api.dto.TokenDto;
import com.bithumb.auth.auth.api.dto.TokenRequestDto;
import com.bithumb.auth.auth.api.dto.UserLoginTarget;
import com.bithumb.auth.auth.api.dto.UserSignUpTarget;
import com.bithumb.auth.auth.application.AuthServiceImpl;
import com.bithumb.auth.auth.domain.RefreshToken;
import com.bithumb.auth.auth.repository.RefreshTokenRepository;
import com.bithumb.auth.security.jwt.TokenProvider;
import com.bithumb.auth.user.domain.Authority;
import com.bithumb.auth.user.domain.User;
import com.bithumb.auth.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

	//@InjectMocks
	AuthServiceImpl authService;
	@Mock
	RefreshTokenRepository refreshTokenRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	PasswordEncoder passwordEncoder;
	@Mock
	AuthenticationManagerBuilder authenticationManagerBuilder;
	@Mock
	TokenProvider tokenProvider;

	//dto
	final UserSignUpTarget signUpTarget = UserSignUpTarget.builder()
		.userId("bithumb")
		.password("root")
		.nickname("nickname")
		.authority(Authority.ROLE_USER)
		.build();

	//dto
	final UserLoginTarget loginTarget = UserLoginTarget.builder()
		.userId("bithumb10")
		.password("bithumb10")
		.build();

	//dto
	final User user = User.builder()
		.id(1l)
		.userId("bithumb")
		.password("root")
		.nickname("nickname")
		.authority(Authority.ROLE_USER)
		.build();


	//dto
	final TokenDto tokenDto = TokenDto.builder()
		.grantType("bearer")
		.accessToken(
			"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTYzMTg5ODgyNH0.rU3GBd4QGwXE0DprckBXmpvNM36YLS3Gk5DUd-CmxgD7o6mF3IjNo4OXlQ2v2XMzaAONfoc73g1hH3mwGU1E6A")
		.refreshToken(
			"eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MzI1MDE4MjR9.ZiWYYiaIElyvaLY8T0YXBqKM5GX-H-2hfFNR0-emaYMmuaigUM15xW6Gpo84530zNksbaVviagj5EDr9FsoC9Q")
		.accessTokenExpiresIn((long)3600 * 24 * 7)
		.build();

	//dto
	final TokenRequestDto tokenRequestDto = TokenRequestDto.builder()
		.accessToken(
			"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTYzMTg5ODgyNH0.rU3GBd4QGwXE0DprckBXmpvNM36YLS3Gk5DUd-CmxgD7o6mF3IjNo4OXlQ2v2XMzaAONfoc73g1hH3mwGU1E6A")
		.refreshToken(
			"eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MzI1MDE4MjR9.ZiWYYiaIElyvaLY8T0YXBqKM5GX-H-2hfFNR0-emaYMmuaigUM15xW6Gpo84530zNksbaVviagj5EDr9FsoC9Q")
		.build();

	//dto
	final RefreshToken refreshToken = RefreshToken.builder()
		.refreshKey("1")
		.refreshValue(
			"eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MzI1MDE4MjR9.ZiWYYiaIElyvaLY8T0YXBqKM5GX-H-2hfFNR0-emaYMmuaigUM15xW6Gpo84530zNksbaVviagj5EDr9FsoC9Q")
		.build();

	//dto
	AuthApiResponse resultInput = AuthApiResponse.builder()
		.id(1l)
		.grantType("bearer")
		.accessToken(
			"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTYzMTg5ODgyNH0.rU3GBd4QGwXE0DprckBXmpvNM36YLS3Gk5DUd-CmxgD7o6mF3IjNo4OXlQ2v2XMzaAONfoc73g1hH3mwGU1E6A")
		.refreshToken(
			"eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MzI1MDE4MjR9.ZiWYYiaIElyvaLY8T0YXBqKM5GX-H-2hfFNR0-emaYMmuaigUM15xW6Gpo84530zNksbaVviagj5EDr9FsoC9Q")
		.accessTokenExpiresIn(604800l)
		.build();

	private GrantedAuthority user1;

	@BeforeEach
	void setUp() {
		this.authService = new AuthServiceImpl(authenticationManagerBuilder, userRepository, passwordEncoder,
			tokenProvider, refreshTokenRepository);
	}

	@Test
	@DisplayName("??????????????? - ????????????")
	void signup() {
		// given
		given(userRepository.existsByUserId(any())).willReturn(false);
		given(userRepository.existsByNickname(any())).willReturn(false);

		// when
		authService.signup(signUpTarget);

		//then
		then(userRepository).should(times(1)).save(any());
	}

	@Test
	@DisplayName("??????????????? - ????????? ?????? ??????")
	void signupDuplicateUserId() {
		// given
		given(userRepository.existsByUserId(any())).willReturn(true);

		// then
		assertThrows(DuplicateKeyException.class, () -> authService.signup(signUpTarget));
	}

	@Test
	@DisplayName("??????????????? - ????????? ?????? ??????")
	void signupDuplicateNickname() {
		// given
		given(userRepository.existsByNickname(any())).willReturn(true);

		// then
		assertThrows(DuplicateKeyException.class, () -> authService.signup(signUpTarget));
	}

	// @Test
	// @DisplayName("??????????????? - ?????????")
	// void login() {
	// 	// given
	// 	Authentication authentication = new UsernamePasswordAuthenticationToken("1", "password");
	// 	given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(user));
	// 	given(tokenProvider.generateTokenDto(any())).willReturn(tokenDto);
	// 	given(refreshTokenRepository.save(any())).willReturn(refreshToken);
	// 	//given(authenticationManagerBuilder.getObject().authenticate(authentication)).willReturn(authentication);
	//
	// 	// when
	// 	AuthApiResponse resultOutput = authService.login(loginTarget);
	//
	// 	// then
	// 	assertThat(resultInput, is(resultOutput));
	//
	// 	then(userRepository).should(times(1)).findByUserId(any());
	// 	then(tokenProvider).should(times(1)).generateTokenDto(any());
	// 	then(refreshTokenRepository).should(times(1)).save(any());
	// 	then(authenticationManagerBuilder).should(times(1)).getObject().authenticate(any());
	//
	// }

	@Test
	@DisplayName("??????????????? - ????????? ????????? ??????x")
	void loginUserIdNotExist() {
		// given
		given(userRepository.findByUserId(any())).willReturn(null);
		// then
		assertThrows(NullPointerException.class, () -> authService.login(loginTarget));
	}

	@Test
	@DisplayName("??????????????? - ?????? ?????????")
	void reissue() {
		Authentication authentication = new UsernamePasswordAuthenticationToken("1", "password");

		// given
		given(tokenProvider.validateToken(any())).willReturn(true);
		given(tokenProvider.getAuthentication(any())).willReturn(authentication);
		given(tokenProvider.generateTokenDto(any())).willReturn(tokenDto);
		given(refreshTokenRepository.findById(any())).willReturn(Optional.ofNullable(refreshToken));
		given(refreshTokenRepository.save(any())).willReturn(refreshToken);
		given(userRepository.findById(any())).willReturn(Optional.ofNullable(user));

		// when
		AuthApiResponse resultOutput = authService.reissue(tokenRequestDto);

		// then
		//assertThat(resultInput, is(resultOutput));

		then(tokenProvider).should(times(1)).validateToken(any());
		then(tokenProvider).should(times(1)).getAuthentication(any());
		then(tokenProvider).should(times(1)).generateTokenDto(any());
		then(refreshTokenRepository).should(times(1)).findById(any());
		then(refreshTokenRepository).should(times(1)).save(any());

	}

	@Test
	@DisplayName("??????????????? - ?????? ????????? ????????? ??????x")
	void reissueUserIdNotExist() {
		// given
		given(tokenProvider.validateToken(any())).willReturn(false);
		// then
		assertThrows(RuntimeException.class, () -> authService.reissue(tokenRequestDto));
	}

	@Test
	@DisplayName("??????????????? - ????????? ?????? ??????")
	void checkDuplicateUserId() {
		// when
		userRepository.existsByUserId("bithumb");
		//then
		then(userRepository).should(times(1)).existsByUserId(any());
	}

	@Test
	@DisplayName("??????????????? - ????????? ?????? ??????")
	void checkDuplicateNickname() {
		// when
		userRepository.existsByNickname("bithumb");
		//then
		then(userRepository).should(times(1)).existsByNickname(any());
	}

}