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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrdersService {

	private final OrdersRepository ordersRepository;
	private final PaymentRepository paymentRepository;
	private final CustomerService customerService;

	@Transactional
	public OrdersResDTO order(OrdersReqDTO ordersReqDTO) {
		Customer customer = customerService.findById(ordersReqDTO.customerId());
		Orders orders = new Orders();
		customer.addOrder(orders);

		for (OrdersItemDTO ordersItem : ordersReqDTO.list()) {
			orders.addOrdersItems(OrdersItem.builder()
					.name(ordersItem.name())
					.price(ordersItem.price())
					.build());
		}
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

	@Transactional(readOnly = true)
	public OrdersListResDTO findOrders(OrdersListReqDTO reqDTO) {
		Page<Orders> ordersPage = ordersRepository.findByCustomerIdOrderByOrdersTimeDesc(
				reqDTO.customerId(), reqDTO.paging().toPageable()
		);
		return OrdersListResDTO.convert(ordersPage);
	}

	@Transactional
	public boolean cancle(int orderId) {
		try {
			Orders orders = ordersRepository.findById(orderId)
					.orElseThrow(IllegalArgumentException::new);
			orders.updateOrdersStatus(OrdersStatus.CANCELED);
			Payment payment = paymentRepository.findByOrdersId(orders.getId());
			payment.setPaymentStatus(PaymentStatus.CANCELED);
		} catch (IllegalArgumentException i) {
			return false;
		}
		return true;
	}

}
