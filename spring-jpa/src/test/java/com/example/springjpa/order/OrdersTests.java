package com.example.springjpa.order;

import com.example.springjpa.TestExecutionListener;
import com.example.springjpa.domain.OrdersStatus;
import com.example.springjpa.domain.PaymentType;
import com.example.springjpa.dto.*;
import com.example.springjpa.entity.vo.Address;
import com.example.springjpa.entity.vo.PaymentInfo;
import com.example.springjpa.entity.vo.UserInfo;
import com.example.springjpa.service.CustomerService;
import com.example.springjpa.service.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("주문관련")
@ExtendWith(TestExecutionListener.class)
public class OrdersTests {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrdersService ordersService;

	@BeforeEach
	@DisplayName("테스트 준비")
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
	@DisplayName("주문")
	void order() {
		List<OrdersItemDTO> ordersItems = new ArrayList<>();
		ordersItems.add(new OrdersItemDTO("감자튀김", 123123));
		ordersItems.add(new OrdersItemDTO("햄버거", 98789));
		OrdersReqDTO orderReqDTO1 = new OrdersReqDTO(
				1, ordersItems, new PaymentInfo(PaymentType.APPLEPAY, ")(DcvjhA")
		);
		OrdersResDTO orderResDTO = ordersService.order(orderReqDTO1);
		assertNotNull(orderResDTO);
	}

	@Test
	@DisplayName("상세 조회")
	void findOrder() {
		List<OrdersItemDTO> ordersItems = new ArrayList<>();
		ordersItems.add(new OrdersItemDTO("테스트메뉴1", 1004));
		ordersItems.add(new OrdersItemDTO("테스트메뉴2", 12456));
		OrdersReqDTO orderReqDTO = new OrdersReqDTO(
				1, ordersItems, new PaymentInfo(PaymentType.APPLEPAY, ")(DcvjhA")
		);
		OrdersResDTO saveOrder = ordersService.order(orderReqDTO);

		OrdersResDTO result = ordersService.findOrder(new OrdersGetReqDTO(saveOrder.orderId(), OrdersStatus.PENDING));
		assertNotNull(result);

		assertThrows(IllegalArgumentException.class, () -> ordersService.findOrder(new OrdersGetReqDTO(saveOrder.orderId(), OrdersStatus.COMPLETED)));
	}

	@Test
	@DisplayName("목록 조회")
	void findOrders() {
		List<OrdersItemDTO> ordersItems1 = new ArrayList<>();
		ordersItems1.add(new OrdersItemDTO("초밥", 100214));
		ordersItems1.add(new OrdersItemDTO("피자", 10023314));
		ordersItems1.add(new OrdersItemDTO("햄버거", 1002114));
		OrdersReqDTO orderReqDTO = new OrdersReqDTO(
				1, ordersItems1, new PaymentInfo(PaymentType.APPLEPAY, ")(DcvjhA")
		);
		ordersService.order(orderReqDTO);

		List<OrdersItemDTO> ordersItems2 = new ArrayList<>();
		ordersItems2.add(new OrdersItemDTO("테스트메뉴1", 1004));
		ordersItems2.add(new OrdersItemDTO("테스트메뉴2", 13515));
		OrdersReqDTO orderReqDTO2 = new OrdersReqDTO(
				1, ordersItems2, new PaymentInfo(PaymentType.APPLEPAY, ")(DcvjhA")
		);
		ordersService.order(orderReqDTO2);

		List<OrdersItemDTO> ordersItems3 = new ArrayList<>();
		ordersItems3.add(new OrdersItemDTO("메뉴1", 25));
		ordersItems3.add(new OrdersItemDTO("메뉴2", 666));
		ordersItems3.add(new OrdersItemDTO("메뉴3", 1577));
		OrdersReqDTO orderReqDTO3 = new OrdersReqDTO(
				1, ordersItems3, new PaymentInfo(PaymentType.SAMSUNGPAY, "*(ㄴㅇㄴㅇ럍ㅊㅋㅍaD")
		);
		ordersService.order(orderReqDTO3);

		OrdersListReqDTO listGetReqDTO = new OrdersListReqDTO(
				1, new Paging(0, 5, null)
		);
		OrdersListResDTO result = ordersService.findOrders(listGetReqDTO);
		assertNotNull(result);
	}

	@Test
	@DisplayName("취소")
	void cancle() {
		List<OrdersItemDTO> ordersItems = new ArrayList<>();
		ordersItems.add(new OrdersItemDTO("감자튀김", 123123));
		ordersItems.add(new OrdersItemDTO("햄버거", 98789));
		OrdersReqDTO orderReqDTO1 = new OrdersReqDTO(
				1, ordersItems, new PaymentInfo(PaymentType.APPLEPAY, ")(DcvjhA")
		);
		OrdersResDTO orderResDTO = ordersService.order(orderReqDTO1);
		int id = orderResDTO.orderId();
		assertTrue(ordersService.cancle(id));
	}

}
