package com.example.springjpa.customer;

import com.example.springjpa.TestExecutionListener;
import com.example.springjpa.dto.CustomerLoginReqDTO;
import com.example.springjpa.dto.CustomerRegisterReqDTO;
import com.example.springjpa.dto.CustomerUpdateReqDTO;
import com.example.springjpa.entity.vo.Address;
import com.example.springjpa.entity.vo.UserInfo;
import com.example.springjpa.service.CustomerService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("사용자")
@ExtendWith(TestExecutionListener.class)
public class CustomerTests {

	@Autowired
	private CustomerService customerService;

	@BeforeEach
	@Tag("사용자 데이터 생성")
	void setUp() {
		System.out.println("\nsetUp - start");
		CustomerRegisterReqDTO customerRegisterReqDTO = new CustomerRegisterReqDTO(
				new UserInfo("test1", "고객1", "test1234"),
				new Address("좋은 도시", "1번가")
		);
		try {
			customerService.register(customerRegisterReqDTO);
		} catch (IllegalArgumentException i) {
			System.out.println("setUp - already");
		}
		System.out.println("setUp - end");
	}

	@Test
	@Tag("회원가입")
	void register() {
		CustomerRegisterReqDTO customerRegisterReqDTO = new CustomerRegisterReqDTO(
				new UserInfo("test", "고객", "test1234"),
				new Address("밝은 도시", "99번가")
		);
		customerService.register(customerRegisterReqDTO);

		CustomerRegisterReqDTO duplregisterReqDTOCustomer = new CustomerRegisterReqDTO(
				new UserInfo("test1", "고객1", "test1234"),
				new Address("좋은 도시", "1번가")
		);
		assertThrows(IllegalArgumentException.class, () ->
			customerService.register(duplregisterReqDTOCustomer)
		);
	}

	@Test
	@Tag("로그인")
	void login() throws BadRequestException {
		CustomerLoginReqDTO login1 = new CustomerLoginReqDTO("test1", "test1234");
		assertNotNull(customerService.login(login1));

		CustomerLoginReqDTO login2 = new CustomerLoginReqDTO("test1", "test4321");
		assertThrows(BadRequestException.class, () ->
			customerService.login(login2)
		);

		CustomerLoginReqDTO login3 = new CustomerLoginReqDTO("test2", "test4321");
		assertThrows(IllegalArgumentException.class, () ->
			customerService.login(login3)
		);
	}

	@Test
	@Tag("회원수정")
	void update() {
		CustomerUpdateReqDTO update1 = new CustomerUpdateReqDTO(
				1,
				new UserInfo(null, "고객1", "test1234"),
				new Address("도시1", "도로1")
		);
		customerService.update(update1);

		CustomerUpdateReqDTO update2 = new CustomerUpdateReqDTO(
				1,
				new UserInfo(null, "고객1", null),
				null
		);
		customerService.update(update2);

		CustomerUpdateReqDTO update3 = new CustomerUpdateReqDTO(
				999,
				new UserInfo(null, "고객1", null),
				null
		);
		assertThrows(IllegalArgumentException.class, () ->
			customerService.update(update3)
		);
	}

}
