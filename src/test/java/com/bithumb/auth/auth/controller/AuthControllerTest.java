package com.bithumb.auth.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.bithumb.auth.auth.api.dto.TokenRequestDto;
import com.bithumb.auth.auth.api.dto.UserLoginRequest;
import com.bithumb.auth.auth.api.dto.UserSignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class AuthControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper;

	//dto
	UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
		.userId("testId")
		.password("testId")
		.nickname("testNickname")
		.build();

	//dto
	UserLoginRequest userLoginRequest = UserLoginRequest.builder()
		.userId("bithumb10")
		.password("bithumb10")
		.deviceToken("aaa")
		.build();


	//dto
	TokenRequestDto tokenRequestDto = TokenRequestDto.builder()
		.accessToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTYzMzk2NTg2NH0.DFVnvXWU5AwS-kHZD8XNwvPi9mCdIaFpeAIufJlykurzvuefGAyYLb0c6XEs9MHC3EMF1SVxpXEHgqTf73sq1Q")
		.refreshToken("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MzQ1Njg4NjR9.XfEE0NQzBXruABnHvszC6UOV8Q9ZrY0oBqztvgwl4TSYTo4nDBvDBOBQGPP95R8akRJ9Brh5Y7heqP5c0-DLNA")
		.build();
	String accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTYzMzk2NTg2NH0.DFVnvXWU5AwS-kHZD8XNwvPi9mCdIaFpeAIufJlykurzvuefGAyYLb0c6XEs9MHC3EMF1SVxpXEHgqTf73sq1Q";

	/**
	 * Controller는 굳이 설명할 필요가 없을것 같다.
	 * 만약 헤더가 필요하거나
	 * Request Param이 필요하다면 다음 과 같이하면 될것 같다.
	 *
	 * 헤더 & RequestBody
	 * mockMvc.perform(put("/users/password/12")
	 * 			.header("Authorization",accessToken)
	 * 			.contentType(MediaType.APPLICATION_JSON)
	 * 			.content(json))
	 * 			.andDo(print())
	 * 			.andExpect(status().isOk());
	 *
	 * 헤더 & RequestBody & RequestParam
	 * mockMvc.perform(put("/users/password?name=sungwon&limit=20"")
	 * 			.header("Authorization",accessToken)
	 * 			.contentType(MediaType.APPLICATION_JSON)
	 * 			.content(json))
	 * 			.andDo(print())
	 * 			.andExpect(status().isOk());
	 */

	@BeforeEach
	void setUp() {
	}

	@Test
	void signup() throws Exception {
		String json = objectMapper.writeValueAsString(userSignUpRequest);

		mockMvc.perform(post("/auth/signup")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
			.andDo(print())
			.andExpect(status().isOk());
		//.andExpect(jsonPath("message").value(SuccessCode.USER_UPDATE_PASSWORD_SUCESS.getMessage()));
	}

	@Test
	void login() throws Exception {
		String json = objectMapper.writeValueAsString(userLoginRequest);

		mockMvc.perform(post("/auth/login")
			//.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void reissue() throws Exception {
		String json = objectMapper.writeValueAsString(tokenRequestDto);

		mockMvc.perform(post("/auth/reissue")
			.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void checkDuplicateId() throws Exception {
		mockMvc.perform(get("/auth/check-duplicate-user-id/bithumb10"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void checkNickname() throws Exception {
		mockMvc.perform(get("/auth/check-duplicate-nickname/bithumb10"))
			.andDo(print())
			.andExpect(status().isOk());
	}
}