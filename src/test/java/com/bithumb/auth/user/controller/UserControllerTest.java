package com.bithumb.auth.user.controller;

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

import com.bithumb.auth.user.api.dto.DeleteUserRequest;
import com.bithumb.auth.user.api.dto.ModifyNicknameRequest;
import com.bithumb.auth.user.api.dto.ModifyPasswordRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper;


	//dto
	final ModifyPasswordRequest modifyPasswordRequest = new ModifyPasswordRequest("55");
	//dto
	final ModifyNicknameRequest modifyNicknameRequest = new ModifyNicknameRequest("555");
	//dto
	final DeleteUserRequest deleteUserRequest = new DeleteUserRequest("bithumb10");
	//token
	String accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2MzM5MzI2Nzh9.8RVu9jJBf1NIWztsJ4-bbUy4WfVoXcAg31fW8IqYXMrTDkg1kBgo_cauHOk5ftlFWVkTPeg7NXiX7Sv0hpW81g";

	@BeforeEach
	void setUp() {
	}

	@Test
	void modifyPassword() throws Exception {
		String json = objectMapper.writeValueAsString(modifyPasswordRequest);

		mockMvc.perform(put("/users/password/11")
			.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
			.andDo(print())
			.andExpect(status().isOk());
			//.andExpect(jsonPath("message").value(SuccessCode.USER_UPDATE_PASSWORD_SUCESS.getMessage()));
	}

	@Test
	void modifyNickname() throws Exception {
		String json = objectMapper.writeValueAsString(modifyNicknameRequest);

		mockMvc.perform(put("/users/nickname/11")
			.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
			.andDo(print())
			.andExpect(status().isOk());
		//.andExpect(jsonPath("message").value(SuccessCode.USER_UPDATE_PASSWORD_SUCESS.getMessage()));
	}

	@Test
	void deleteUserInfo() throws Exception {
		String json = objectMapper.writeValueAsString(deleteUserRequest);

		mockMvc.perform(delete("/users/11/info")
			.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
			.andDo(print())
			.andExpect(status().isOk());
		//.andExpect(jsonPath("message").value(SuccessCode.USER_UPDATE_PASSWORD_SUCESS.getMessage()));
	}


}