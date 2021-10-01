package com.bithumb.auth.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

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

	private GrantedAuthority user1;

	@BeforeEach
	void setUp() {
		this.authService = new AuthServiceImpl(authenticationManagerBuilder, userRepository, passwordEncoder,
			tokenProvider, refreshTokenRepository);
	}

	@Test
	@DisplayName("성공테스트 - 회원가입")
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
	@DisplayName("실패테스트 - 아이디 중복 실패")
	void signupDuplicateUserId() {
		// given
		given(userRepository.existsByUserId(any())).willReturn(true);

		// then
		assertThrows(DuplicateKeyException.class, () -> authService.signup(signUpTarget));
	}

	@Test
	@DisplayName("실패테스트 - 닉네임 중복 실패")
	void signupDuplicateNickname() {
		// given
		given(userRepository.existsByNickname(any())).willReturn(true);

		// then
		assertThrows(DuplicateKeyException.class, () -> authService.signup(signUpTarget));
	}

/*	@Test
	@DisplayName("성공테스트 - 로그인")
	void login() {
		// given
		//given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(user));
		//given(refreshTokenRepository.save(any())).willReturn(refreshToken);

		System.out.println(loginTarget.toString());
		// when
		UsernamePasswordAuthenticationToken authenticationToken = loginTarget.toAuthentication();
		System.out.println(authenticationToken);
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		System.out.println(authentication);
		//then
		//then(refreshTokenRepository).should(times(1)).save(any());
	}*/

	//UserLoginTarget(userId=bithumb10, password=bithumb10)
	//UsernamePasswordAuthenticationToken [Principal=bithumb10, Credentials=[PROTECTED], Authenticated=false, Details=null, Granted Authorities=[]]
	//UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=12, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[ROLE_USER]]

	@Test
	@DisplayName("실패테스트 - 로그인 아이디 존재x")
	void loginUserIdNotExist() {
		// given
		given(userRepository.findByUserId(any())).willReturn(null);
		// then
		assertThrows(NullPointerException.class, () -> authService.login(loginTarget));
	}


/*	@Test
	void reissue() {
		Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
		System.out.println(authentication);
	}*/

	@Test
	@DisplayName("실패테스트 - 토큰 재발급 아이디 존재x")
	void reissueUserIdNotExist() {
		// given
		given(tokenProvider.validateToken(any())).willReturn(false);
		// then
		assertThrows(RuntimeException.class, () -> authService.reissue(tokenRequestDto));
	}

	@Test
	@DisplayName("성공테스트 - 아이디 중복 체크")
	void checkDuplicateUserId() {
		// when
		userRepository.existsByUserId("bithumb");
		//then
		then(userRepository).should(times(1)).existsByUserId(any());
	}

	@Test
	@DisplayName("성공테스트 - 닉네임 중복 체크")
	void checkDuplicateNickname() {
		// when
		userRepository.existsByNickname("bithumb");
		//then
		then(userRepository).should(times(1)).existsByNickname(any());
	}

}