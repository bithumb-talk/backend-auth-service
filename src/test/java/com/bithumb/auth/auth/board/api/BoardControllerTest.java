package com.bithumb.auth.auth.board.api;

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

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class BoardControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper;


	String accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTYzMzk2NTg2NH0.DFVnvXWU5AwS-kHZD8XNwvPi9mCdIaFpeAIufJlykurzvuefGAyYLb0c6XEs9MHC3EMF1SVxpXEHgqTf73sq1Q";

	@BeforeEach
	void setUp() {

	}

	@Test
	void checkLikeBoardContent() throws Exception {
		mockMvc.perform(post("/user-boards/6/like-board-content/1")
			.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON))
			//.content(json))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void cancleLikeBoardContent() throws Exception {
		mockMvc.perform(delete("/user-boards/11/like-board-content/1")
			.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON))
			//.content(json))
			.andDo(print())
			.andExpect(status().is4xxClientError());
	}

	@Test
	void findLikeBoardContentByUserId() throws Exception {
		mockMvc.perform(get("/user-boards/6/like-board-contents")
			.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON))
			//.content(json))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void matchingBoardLikeContent() throws Exception {
		mockMvc.perform(get("/user-boards/6/like-board-content/1")
			.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON))
			//.content(json))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void checkLikeCommentContent() throws Exception {
		mockMvc.perform(post("/user-boards/6/like-comment-content/1")
			.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON))
			//.content(json))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void cancleLikeCommentContent() throws Exception {
		mockMvc.perform(delete("/user-boards/6/like-comment-content/1")
			.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON))
			//.content(json))
			.andDo(print())
			.andExpect(status().is4xxClientError());
	}

	@Test
	void findLikeCommentContentByUserId() throws Exception {
		mockMvc.perform(get("/user-boards/6/like-comment-contents")
			.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON))
			//.content(json))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void matchingCommentLikeContent() throws Exception {
		mockMvc.perform(get("/user-boards/6/like-comment-content/1")
			.header("Authorization",accessToken)
			.contentType(MediaType.APPLICATION_JSON))
			//.content(json))
			.andDo(print())
			.andExpect(status().isOk());
	}
}