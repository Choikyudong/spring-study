package com.example.springsecurity;

import com.example.springsecurity.dto.UserLoginResDTO;
import com.example.springsecurity.dto.UsersRegisterReqDTO;
import com.example.springsecurity.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsersService usersService;

	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	@DisplayName("인증없는 Get 테스트")
	void getTest() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("회원가입 요청")
	void singUp() throws Exception {
		mockMvc.perform(post("/signUp")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								"""
								{
									"userName": "user",\s
									"password": "password1234",\s
									"email": "test@test.com"\s
								}
								"""
						))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("로그인")
	void login() throws Exception {
		// 테스트를 위해 사용자 생성
		usersService.signUp(new UsersRegisterReqDTO("user", "password1234", "test@test.com", null));

		MvcResult result = mockMvc.perform(post("/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"userName": "user",\s
									"password": "password1234"\s
								}
								"""))
						.andExpect(status().isOk())
						.andReturn();
		String json = result.getResponse().getContentAsString();
		assertNotNull(mapper.readValue(json, UserLoginResDTO.class));
	}

}
