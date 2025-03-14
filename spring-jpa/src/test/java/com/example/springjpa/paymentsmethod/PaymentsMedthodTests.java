package com.example.springjpa.paymentsmethod;

import com.example.springjpa.TestExecutionListener;
import com.example.springjpa.domain.PaymentType;
import com.example.springjpa.dto.PaymentMethodDeleteReqDTO;
import com.example.springjpa.dto.PaymentMethodRegisterReqDTO;
import com.example.springjpa.dto.PaymentMethodUpdateReqDTO;
import com.example.springjpa.dto.CustomerRegisterReqDTO;
import com.example.springjpa.entity.PaymentMethod;
import com.example.springjpa.entity.vo.Address;
import com.example.springjpa.entity.vo.PaymentInfo;
import com.example.springjpa.entity.vo.UserInfo;
import com.example.springjpa.service.CustomerService;
import com.example.springjpa.service.PaymentMethodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("결제수단")
@ExtendWith(TestExecutionListener.class)
public class PaymentsMedthodTests {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@BeforeEach
	@DisplayName("사용자 생성")
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
	@DisplayName("목록 조회")
	void getList() {
		int customerId = 1;
		assertTrue(paymentMethodService.getList(customerId).isEmpty());

		PaymentMethodRegisterReqDTO paymentsReqDTO = new PaymentMethodRegisterReqDTO(
				1, "APPLE", new PaymentInfo(PaymentType.APPLEPAY, "d32iuoc0ads")
		);
		paymentMethodService.register(paymentsReqDTO);

		assertFalse(paymentMethodService.getList(customerId).isEmpty());
	}

	@Test
	@DisplayName("등록")
	void register() {
		PaymentMethodRegisterReqDTO paymentsReqDTO = new PaymentMethodRegisterReqDTO(
				1, "APPLE", new PaymentInfo(PaymentType.APPLEPAY, "d32iuoc0ads")
		);
		assertFalse(paymentMethodService.register(paymentsReqDTO).isEmpty());
	}

	@Test
	@DisplayName("수정")
	void update() {
		// 등록
		PaymentMethodRegisterReqDTO paymentsReqDTO = new PaymentMethodRegisterReqDTO(
				1, "APPLE", new PaymentInfo(PaymentType.APPLEPAY, "d32iuoc0ads")
		);
		List<PaymentMethod> registerList = paymentMethodService.register(paymentsReqDTO);
		assertNotNull(registerList);

		int nextId = registerList.get(0).getId();

		// 수정
		PaymentMethodUpdateReqDTO paymentsUpdateDTO1 = new PaymentMethodUpdateReqDTO(
				nextId, 1, "NAVER", new PaymentInfo(PaymentType.NAVERPAY, "NASui3y2413")
		);
		List<PaymentMethod> paymentResDTOList = paymentMethodService.update(paymentsUpdateDTO1);
		assertNotNull(paymentResDTOList);

		PaymentMethodUpdateReqDTO paymentsUpdateDTO2 = new PaymentMethodUpdateReqDTO(
				999, 1, "KAKAKA", new PaymentInfo(PaymentType.KAKAOPAY, "dasf1234@#DS")
		);
		assertThrows(IllegalArgumentException.class, () -> paymentMethodService.update(paymentsUpdateDTO2));

		PaymentMethodUpdateReqDTO paymentsUpdateDTO3 = new PaymentMethodUpdateReqDTO(
				nextId, 2, "SAMSAM", new PaymentInfo(PaymentType.SAMSUNGPAY, "dszf389Ae4fghDF")
		);
		assertThrows(IllegalArgumentException.class, () -> paymentMethodService.update(paymentsUpdateDTO3));
	}

	@Test
	@DisplayName("삭제")
	void delete() {
		// 테스트용
		PaymentMethodRegisterReqDTO paymentsReqDTO = new PaymentMethodRegisterReqDTO(
				1, "APPLE", new PaymentInfo(PaymentType.APPLEPAY, "d32iuoc0ads")
		);
		paymentMethodService.register(paymentsReqDTO);

		// 요청
		PaymentMethodDeleteReqDTO deleteReqDTO = new PaymentMethodDeleteReqDTO(1, 1);
		assertTrue(paymentMethodService.delete(deleteReqDTO));

		// 실패 요청
		PaymentMethodDeleteReqDTO failTest = new PaymentMethodDeleteReqDTO(124, 2);
		assertFalse(paymentMethodService.delete(failTest));
	}

}
