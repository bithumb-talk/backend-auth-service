package com.bithumb.auth.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bithumb.auth.auth.repository.RefreshTokenRepository;
import com.bithumb.auth.security.authentication.AuthInfo;
import com.bithumb.auth.user.api.dto.DeleteUserTarget;
import com.bithumb.auth.user.api.dto.ModifyNicknameTarget;
import com.bithumb.auth.user.api.dto.ModifyPasswordTarget;
import com.bithumb.auth.user.application.S3Uploader;
import com.bithumb.auth.user.application.UserServiceImpl;
import com.bithumb.auth.user.domain.Authority;
import com.bithumb.auth.user.domain.User;
import com.bithumb.auth.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	//https://doyoung.tistory.com/12
	//todo. InjectMocks 사용x

	/**
	 * 설명하기에 앞서
	 * 제가 작성한 방식은 단위테스트의 극히 일부분이다.
	 * mock을 설명하기 위해서는
	 * stub이나 spy,mock 등등 다양한 개념이 필요하지만
	 * 저는 제가 작성하고 아는 부분만 설명하겠습니다.
	 */

	/**
	 * 	통합테스트가 아닌 단위테스트를 하는 이유는 뭘까?
	 *
	 * 	1. 통합테스트는 데이터베이스 의존적이다
	 * 	2. 통합테스트는 동작시간이 너무 오래걸린다.
	 * 	> 실제 기업 프로젝트는 큰 단위이기 때문에 프로젝트 실행하는데 몇분이 걸릴 정도이다.
	 * 	> 따라서 실제 기업에서 실행하지 않고 테스트코드를 통해 동작을 테스트 하고
	 *  > 그래서 중요한 것이 TDD이다. 프로젝트를 실행해 볼 수 없으니깐
	 *  > 결론! 단위테스트가 젤 중요하다. 요즘은 인수테스트도 대세..!
	 * 	3. 즉, 데이터베이스를 사용하지 않고 동작을 검증하기 위한 테스트가 단위테스트 이다. (좁은 의미)
	 * 	3. 즉, 외부의 의존성에 영향을 받지 않고 동작을 검증하기 위한 테스트가 단위테스트 이다 (넓은 의미)
	 */

	/**
	 * @Mock이 붙은 목객체를 @InjectMocks이 붙은 객체에 주입시킬 수 있다.
	 *
	 * 간단하게 UserServiceImpl에 선언된
	 * UserServiceImpl userServiceImpl > @InjectMocks
	 *
	 * private final UserRepository userRepository > @Mock
	 * private final PasswordEncoder passwordEncoder > @Mock
	 * private fina RefeshTokenRepository refreshTokenRepository > @Mock
	 */

	// 가짜 객체(목)을 주입하려는 클래스
	//@InjectMocks
	UserServiceImpl userService;
	// 가짜 객체
	@Mock
	UserRepository userRepository;
	@Mock
	PasswordEncoder passwordEncoder;
	@Mock
	RefreshTokenRepository refreshTokenRepository;
	@Mock
	S3Uploader s3Uploader;

	/**
	 * 이렇게 가짜 객체를 사용하는 이유는 뭘까?
	 *
	 * Spring은 DI(Dependency Injection)를 지원해주는데, 이는 객체 간의 의존성을 Spring이 관리해주는 것이다.
	 * 그 덕분에 개발자는 의존성 주입을 신경 쓰지 않고 객체 간의 의존 관계만 잘 고민해서 객체를 설계하면 된다.
	 * 그런데 이런 의존성은 테스트를 하는 시점에서 문제를 발생시킨다. 단위 테스트를 작성할 경우 해당 객체에 대한 기능만을
	 * 테스트하고 싶은데 의존성을 가지는 다른 객체에 의해 테스트 결과가 영향을 받을 수 있다는 것이다.
	 * > 즉, 데이터베이스에 저장된 값에 영향을 받는다는 뜻이다.
	 * > 순수하게 동작을 테스트하고 싶은데..
	 * 이렇게 의존을 가지는 객체를 우리가 원하는 동작만 하도록 만든 것이 Mock 객체이다.
	 */

	//dto
	final User user = User.builder()
		.id(1l)
		.userId("bithumb")
		.password("root")
		.nickname("nickname")
		.authority(Authority.ROLE_USER)
		.build();

	//dto
	final ModifyPasswordTarget modifyPasswordTarget = ModifyPasswordTarget.builder()
		.id(1l)
		.password("rootUser")
		.build();

	//dto
	final ModifyNicknameTarget modifyNicknameTarget = ModifyNicknameTarget.builder()
		.id(1l)
		.nickname("nickname")
		.build();

	//dto
	final DeleteUserTarget deleteUserTarget = DeleteUserTarget.builder()
		.id(1l)
		.password("root")
		.build();

	//dto
	final AuthInfo authInfo = AuthInfo.UserOf(1l);

	@BeforeEach
	void setUp() {

		/**
		 * InjectMock을 사용하지 않는 대신 생성자를 직접 선언해 주었다. // 인스턴스를 직접 생성해 주었다.
		 * InjectMock을 사용하지 않는 이유는 어떤 의존성 역전이 주입되는지 직관적으로 파악하기 위함이다..
		 * 솔직히 왜 이렇게 하는게 좋은지는 이해되지는 않지만 클린코드로 많이들 사용하는 방식이다.
		 */

		this.userService = new UserServiceImpl(userRepository, passwordEncoder, refreshTokenRepository, s3Uploader);

		/**
		 * 추가적으로 기업에서 롬복을 너무 많이 사용하는 것을 좋아하지 않는 팀도 많다고 한다!
		 * 그 이유는 본인의 코드이기 때문에 롬복이 이해가 되고 편리하지만
		 * 다른 사람이 보았을때 ""직관성""이 떨어지기 떄문에 너무 빈번한 롬복 사용을 피한다고 한다.
		 *
		 * 위에서 InjectMock을 사용하지 않는 것도 같은 방식인것 같다.
		 * 인스턴스를 직접 생성해 줌으로써 어떤 값이 주입되었는지 더 직관적이기 때문이다!
		 */

	}

	/**
	 * Mockito vs BBDMockito
	 * 우리는 BBDMockito!
	 *
	 * BDD는 Behavior-Driven Development의 약자로 행위 주도 개발을 말한다.
	 * 테스트 대상의 상태의 변화를 테스트하는 것이고,
	 * 시나리오를 기반으로 테스트하는 패턴을 권장한다.
	 *
	 * Mockito
	 * > when    ex) when().thenReturn();
	 * > given
	 * > then
	 *
	 * BBDMockito
	 * > given    ex) given().willReturn();
	 * > when
	 * > then
	 *
	 * BBDMockito가 좋은 이유는
	 * given > when > then 구조가 개발할때 순서상 맞기때문이라고 한다...
	 *
	 * when > given > then은 역순으로 구현하는 거라고 한다..
	 */

	@Test
	@DisplayName("성공테스트 - 패스워드 변경")
	void changePassword() {
		Optional<User> optionalUser = Optional.ofNullable(user);

		// given
		given(userRepository.findById(any())).willReturn(optionalUser);
		given(passwordEncoder.encode(any())).willReturn("user");
		given(userRepository.save(any())).willReturn(user);

		/**
		 * given에는 메소드에서 DI( 의존성 역전 / "@Mock" )을
		 * 주입 받아 사용하고 있는 것들을 정의해 주어야 한다.
		 * 왜? 단위테스트는 의존성에 영향을 받지 않고 동작을 테스트하는 것이니깐!
		 *
		 * 즉, 메소드에서 찾아보고 사용하고 있는거 다 given으로 선언해 준다고 생각하면 된다!
		 * any()는 우리는 데이터베이스에 접근하는 것이 아니다! 즉, 어떤 값이든 들어가게 해도 된다.
		 * 단 willReturn은 우리가 원하는 값을 직접 지정해 주어야 한다.
		 * 왜? 데이터베이스에 접근해서 실제로 값을 받아오는게 아니니깐! 우리가 직접 원하는 값을 입력!
		 *
		 */

		// when
		userService.changePassword(modifyPasswordTarget, authInfo);

		/**
		 * 위에서 given으로 외부 의존성의 반환값을 직접
		 * 우리가 원하는 값으로 설정해 주었다.
		 *
		 * 즉, 이제는 메소드를 호출하였을때 정상적으로 동작해야한다.
		 * 따라서, when에서는 우리가 원하는 메소드를 호출해 준다!		 *
		 */

		//then
		then(userRepository).should(times(1)).save(any());
		//Assertions.assertThat(aa).isEqualTo(userResponseDto);

		/**
		 * then은 결과를 리턴하는 곳이다.
		 * Assertions.assertThat을 사용해도 되고
		 * then을 사용해도 된다!
		 *
		 * 여기서 then().should(times()).~동작~(any());
		 * 여기서 times의 뜻은 메소드를 호출할 동한 한 번 호출되었다는 뜻이다.
		 * 내가 메소드에서 userRepository.save를 두 번 호출했으면
		 * times = 2 !!
		 */

	}

	/**
	 * 마지막으로 단위테스트는
	 * 성공테스트 보다
	 * 실패테스트가 더 중요하다!
	 *
	 * 실패를 통해 동작을 더 정확히 테스트 할 수 있는것 같다!
	 */

	@Test
	@DisplayName("실패테스트 - 패스워드 변경 / 아이디 존재 x")
	void changePasswordUserIdNotExist() {
		// given
		given(userRepository.findById(any())).willReturn(null);

		// then
		assertThrows(NullPointerException.class, () -> userService.changePassword(modifyPasswordTarget, authInfo));
	}

	@Test
	@DisplayName("실패테스트 - 패스워드 변경 / 아이디 불일치")
	void changePasswordUserIdNotMatch() {
		//dto
		final ModifyPasswordTarget failModifyPassword = ModifyPasswordTarget.builder()
			.id(2l)
			.password("rootUser")
			.build();

		// then
		assertThrows(SecurityException.class, () -> userService.changePassword(failModifyPassword, authInfo));
	}

	@Test
	@DisplayName("성공테스트 - 닉네임 변경")
	void changeNickname() {
		Optional<User> optionalUser = Optional.ofNullable(user);

		// given
		given(userRepository.findById(any())).willReturn(optionalUser);
		given(userRepository.save(any())).willReturn(user);

		// when
		userService.changeNickname(modifyNicknameTarget, authInfo);

		//then
		then(userRepository).should(times(1)).save(any());
	}

	@Test
	@DisplayName("실패테스트 - 닉네임 변경 아이디 존재 x")
	void changeNicknameUserIdNotExist() {
		// given
		given(userRepository.findById(any())).willReturn(null);

		// then
		assertThrows(NullPointerException.class, () -> userService.changeNickname(modifyNicknameTarget, authInfo));
	}

	@Test
	@DisplayName("실패테스트 - 닉네임 변경 / 아이디 불일치")
	void changeNicknameUserIdNotMatch() {
		//dto
		final ModifyNicknameTarget failModifyNickname = ModifyNicknameTarget.builder()
			.id(2l)
			.nickname("nickname")
			.build();

		// then
		assertThrows(SecurityException.class, () -> userService.changeNickname(failModifyNickname, authInfo));
	}

	@Test
	@DisplayName("성공테스트 - 회원 삭제")
	void deleteUser() {
		Optional<User> optionalUser = Optional.ofNullable(user);

		// given
		given(userRepository.findById(any())).willReturn(optionalUser);
		given(passwordEncoder.matches(any(), any())).willReturn(true);

		// when
		userService.deleteUser(deleteUserTarget, authInfo);

		//then
		then(userRepository).should(times(1)).deleteById(any());
		then(refreshTokenRepository).should(times(1)).deleteById(any());
	}

	@Test
	@DisplayName("실패테스트 - 회원 삭제 / 아이디 존재 x")
	void deleteUserButIdNotExist() {
		// given
		given(userRepository.findById(any())).willReturn(null);

		// then
		assertThrows(NullPointerException.class, () -> userService.deleteUser(deleteUserTarget, authInfo));
	}

	@Test
	@DisplayName("실패테스트 - 회원 삭제 / 아이디 불일치")
	void deleteUserButIdNotMatch() {
		//dto
		final DeleteUserTarget faildeleteUser = DeleteUserTarget.builder()
			.id(2l)
			.password("root")
			.build();

		Optional<User> optionalUser = Optional.ofNullable(user);

		// given
		given(userRepository.findById(any())).willReturn(optionalUser);

		// then
		assertThrows(SecurityException.class, () -> userService.deleteUser(faildeleteUser, authInfo));
	}

	@Test
	@DisplayName("실패테스트 - 회원 삭제 / 패스워드 불일치")
	void deleteUserButPasswordNotMatch() {
		//dto
		final DeleteUserTarget faildeleteUser = DeleteUserTarget.builder()
			.id(1l)
			.password("failPassword")
			.build();

		Optional<User> optionalUser = Optional.ofNullable(user);

		// given
		given(userRepository.findById(any())).willReturn(optionalUser);

		// then
		assertThrows(SecurityException.class, () -> userService.deleteUser(faildeleteUser, authInfo));
	}

	@Test
	@DisplayName("성공테스트 - 유저 프로필 등록")
	void saveUserProfile() throws IOException {
		Optional<User> optionalUser = Optional.ofNullable(user);
		String fileName = "testCustomerUpload";
		String contentType = "image/jpeg";
		String filePath = "C:\\Users\\ensu7\\Downloads\\image.png";
		MockMultipartFile mockMultipartFile = getMockMultipartFile(fileName, contentType, filePath);
		String resultImg = "https://youngcha-auth-service.s3.ap-northeast-2.amazonaws.com/user/1";

		// given
		given(userRepository.findById(any())).willReturn(optionalUser);
		given(s3Uploader.upload(mockMultipartFile,1l)).willReturn(resultImg);
		// when
		userService.saveProfileImg(1l,mockMultipartFile);

		//then
		//then(userRepository).should(times(1)).saveUserProfileImg(1l,resultImg);
	}

	private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(new File(path));
		return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
	}

}
