package com.example.springjpa.customer;

import com.example.springjpa.TeestExecutionListener;
import com.example.springjpa.dto.LoginReqDTO;
import com.example.springjpa.dto.RegisterReqDTO;
import com.example.springjpa.dto.UpdateReqDTO;
import com.example.springjpa.entity.Address;
import com.example.springjpa.service.CustomerService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("사용자")
@Transactional
@ExtendWith(TeestExecutionListener.class)
public class CustomerTests {

	@Autowired
	private CustomerService customerService;

	@BeforeEach
	@Tag("테스트 준비")
	void setUp() {
		System.out.println("\nsetUp - start");
		RegisterReqDTO registerReqDTO = new RegisterReqDTO(
				"test1",
				"고객1",
				"test1234",
				"좋은 도시",
				"1번가"
		);
		customerService.register(registerReqDTO);
		System.out.println("setUp - end");
	}

	@Test
	@Tag("회원가입")
	void register() {
		RegisterReqDTO registerReqDTO = new RegisterReqDTO(
			"test",
			"고객",
			"test1234",
			"밝은 도시",
			"99번가"
		);
		customerService.register(registerReqDTO);

		RegisterReqDTO duplregisterReqDTO = new RegisterReqDTO(
				"test1",
				"고객1",
				"test1234",
				"좋은 도시",
				"1번가"
		);
		assertThrows(IllegalArgumentException.class, () ->
			customerService.register(duplregisterReqDTO)
		);
	}

	@Test
	@Tag("로그인")
	void login() throws BadRequestException {
		LoginReqDTO login1 = new LoginReqDTO("test1", "test1234");
		assertNotNull(customerService.login(login1));

		LoginReqDTO login2 = new LoginReqDTO("test1", "test4321");
		assertThrows(BadRequestException.class, () ->
			customerService.login(login2)
		);

		LoginReqDTO login3 = new LoginReqDTO("test2", "test4321");
		assertThrows(IllegalArgumentException.class, () ->
			customerService.login(login3)
		);
	}

	@Test
	@Tag("회원수정")
	void update() {
		UpdateReqDTO update1 = new UpdateReqDTO(
				1,
				"고객1",
				"1234",
				Address.builder()
						.city("도시1")
						.street("지번1")
						.build()
		);
		customerService.update(update1);

		UpdateReqDTO update2 = new UpdateReqDTO(
				1,
				"고객4",
				null,
				null
		);
		customerService.update(update2);

		UpdateReqDTO update3 = new UpdateReqDTO(
				999,
				"고객1",
				null,
				null
		);
		assertThrows(IllegalArgumentException.class, () ->
			customerService.update(update3)
		);
	}

}
