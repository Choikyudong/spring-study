package com.example.springjpa.dto;


import com.example.springjpa.domain.OrdersStatus;
import com.example.springjpa.entity.Orders;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record OrdersListResDTO(int price, int size, List<OrdersRes> ordersList) {

	public static OrdersListResDTO convert(Page<Orders> ordersPage) {
		List<OrdersRes> ordersList = new ArrayList<>();
		for (Orders orders : ordersPage.getContent()) {
			ordersList.add(
					new OrdersRes(
							orders.getId(),
							orders.getOrdersTime(),
							orders.getOrdersStatus()
					)
			);
		}
		return new OrdersListResDTO(
				ordersPage.getTotalPages(),
				ordersPage.getSize(),
				ordersList
		);
	}

	record OrdersRes(int id, LocalDateTime ordersTime, OrdersStatus ordersStatus) {
	}

}
