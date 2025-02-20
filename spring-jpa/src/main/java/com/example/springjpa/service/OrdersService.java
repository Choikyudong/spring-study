package com.example.springjpa.service;

import com.example.springjpa.domain.OrdersStatus;
import com.example.springjpa.domain.PaymentStatus;
import com.example.springjpa.dto.*;
import com.example.springjpa.entity.Customer;
import com.example.springjpa.entity.Orders;
import com.example.springjpa.entity.OrdersItem;
import com.example.springjpa.entity.Payment;
import com.example.springjpa.repository.OrdersRepository;
import com.example.springjpa.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrdersService {

	private final OrdersRepository ordersRepository;
	private final PaymentRepository paymentRepository;
	private final CustomerService customerService;

	public OrdersResDTO order(OrdersReqDTO ordersReqDTO) {
		Customer customer = customerService.findCustomer(ordersReqDTO.customerId());
		Orders orders = new Orders();
		orders.setCustomer(customer);
		orders.setOrdersStatus(OrdersStatus.PENDING);

		List<OrdersItem> ordersItems = ordersReqDTO.list().stream()
				.map(reqDTO -> OrdersItem.builder()
						.orders(orders)
						.name(reqDTO.name())
						.price(reqDTO.price())
						.build())
				.toList();
		orders.setOrdersItems(ordersItems);
		Orders result = ordersRepository.save(orders);

		// 결제 요청...
		Payment payment = Payment.builder()
				.customer(customer)
				.orders(result)
				.paymentInfo(ordersReqDTO.paymentInfo())
				.paymentStatus(PaymentStatus.DONE)
				.build();
		paymentRepository.save(payment);
		return OrdersResDTO.convertOrderToOrderResDTO(result);
	}

	public OrdersResDTO findOrder(OrdersGetReqDTO reqDTO) {
		Orders orders = ordersRepository.findOrderWithItems(reqDTO.id(), reqDTO.ordersStatus())
				.orElseThrow(IllegalArgumentException::new);
		return OrdersResDTO.convertOrderToOrderResDTO(orders);
	}

	public OrdersListResDTO findOrders(OrdersListReqDTO reqDTO) {
		Page<Orders> ordersPage = ordersRepository.findByCustomerIdOrderByOrdersTimeDesc(
				reqDTO.customerId(), reqDTO.paging().toPageable()
		);
		return OrdersListResDTO.convert(ordersPage);
	}

	public boolean cancle(int orderId) {
		try {
			Orders orders = ordersRepository.findById(orderId)
					.orElseThrow(IllegalArgumentException::new);
			orders.setOrdersStatus(OrdersStatus.CANCELED);
			Payment payment = paymentRepository.findByOrdersId(orders.getId());
			payment.setPaymentStatus(PaymentStatus.CANCELED);
		} catch (IllegalArgumentException i) {
			return false;
		}
		return true;
	}

}
