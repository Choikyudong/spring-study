package com.example.springsecurity;

import com.example.springsecurity.domain.Role;
import com.example.springsecurity.dto.UserLoginResDTO;
import com.example.springsecurity.dto.UsersSignUpReqDTO;
import com.example.springsecurity.dto.UsersUpdateResDTO;
import com.example.springsecurity.entity.Users;
import com.example.springsecurity.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

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
	@DisplayName("User Get 테스트")
	void getTestUser() throws Exception {
		mockMvc.perform(get("/user"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("회원가입 요청")
	void singUp() throws Exception {
		mockMvc.perform(post("/user/signUp")
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

		mockMvc.perform(post("/user/signUp")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								"""
								{
									"userName": "admin",\s
									"password": "admin1234",\s
									"email": "admin@admin.com",\s
									"roleList": ["ADMIN"]\s
								}
								"""
						))
				.andExpect(status().isCreated());

		mockMvc.perform(post("/user/signUp")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								"""
								{
									"userName": "superAdmin",\s
									"password": "superAdmin1234",\s
									"email": "superAdmin@superAdmin.com",\s
									"roleList": ["ADMIN", "USER"]\s
								}
								"""
						))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("로그인")
	void login() throws Exception {
		// 테스트를 위해 사용자 생성
		usersService.signUp(new UsersSignUpReqDTO("user", "password1234", "test@test.com", null));

		MvcResult result = mockMvc.perform(post("/user/login")
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

	@Test
	@WithAnonymousUser
	@DisplayName("업데이트")
	void update() throws Exception {
		// 테스트 사전 준비
		usersService.signUp(new UsersSignUpReqDTO("user", "password1234", "test@test.com", null));
		MvcResult login = mockMvc.perform(post("/user/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"userName": "user",\s
									"password": "password1234"\s
								}
								"""))
				.andExpect(status().isOk())
				.andReturn();
		String json = login.getResponse().getContentAsString();
		UserLoginResDTO loginResDTO = mapper.readValue(json, UserLoginResDTO.class);

		MvcResult update = mockMvc.perform(patch("/user/update")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResDTO.token())
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"userName": "user",\s
									"password": "changepwd1234",\s
									"email": "test@test.net"\s
								}
								"""))
				.andExpect(status().isAccepted())
				.andReturn();
		String result = update.getResponse().getContentAsString();
		UsersUpdateResDTO updateResDTO = mapper.readValue(result, UsersUpdateResDTO.class);

		Users users = usersService.loadUserByUsername("user");
		assertEquals(users.getEmail(), updateResDTO.email());
	}

	@Test
	@WithAnonymousUser
	@DisplayName("비활성화")
	void deactivate() throws Exception {
		// 테스트 사전 준비
		usersService.signUp(new UsersSignUpReqDTO("user", "password1234", "user@test.com", List.of(Role.USER)));
		usersService.signUp(new UsersSignUpReqDTO("admin", "password1234", "admin@test.com", List.of(Role.ADMIN)));
		MvcResult adminLogin = mockMvc.perform(post("/user/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"userName": "admin",\s
									"password": "password1234"\s
								}
								"""))
				.andExpect(status().isOk())
				.andReturn();
		String json = adminLogin.getResponse().getContentAsString();
		UserLoginResDTO loginResDTO = mapper.readValue(json, UserLoginResDTO.class);

		mockMvc.perform(patch("/admin/user/{id}/deactivate", 1)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResDTO.token()))
						.andExpect(status().isAccepted());

		mockMvc.perform(post("/user/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"userName": "user",\s
									"password": "password1234"\s
								}
								"""))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Admin Get 테스트")
	void getTestAdmin() throws Exception {
		// admin 준비
		usersService.signUp(new UsersSignUpReqDTO("admin", "password1234", "admin@test.com", List.of(Role.ADMIN)));
		MvcResult adminLogin = mockMvc.perform(post("/user/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"userName": "admin",\s
									"password": "password1234"\s
								}
								"""))
				.andExpect(status().isOk())
				.andReturn();
		String adminJson = adminLogin.getResponse().getContentAsString();
		UserLoginResDTO adminLoginResDTO = mapper.readValue(adminJson, UserLoginResDTO.class);

		mockMvc.perform(get("/admin")
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + adminLoginResDTO.token())
				).andExpect(status().isOk());

		// user 준비
		usersService.signUp(new UsersSignUpReqDTO("user", "password1234", "user@test.com", List.of(Role.USER)));
		MvcResult userLogin = mockMvc.perform(post("/user/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"userName": "user",\s
									"password": "password1234"\s
								}
								"""))
				.andExpect(status().isOk())
				.andReturn();
		String userJson = userLogin.getResponse().getContentAsString();
		UserLoginResDTO userLoginResDTO = mapper.readValue(userJson, UserLoginResDTO.class);
		mockMvc.perform(get("/admin")
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + userLoginResDTO.token())
				).andExpect(status().isForbidden());
	}

}
