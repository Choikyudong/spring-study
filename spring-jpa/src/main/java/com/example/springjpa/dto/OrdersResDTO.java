package com.example.springjpa.dto;

import com.example.springjpa.domain.OrdersStatus;
import com.example.springjpa.entity.Orders;
import com.example.springjpa.entity.OrdersItem;

import java.util.ArrayList;
import java.util.List;

public record OrdersResDTO(int orderId, long totalPrice, List<OrdersItemDTO> list, OrdersStatus ordersStatus) {

	public static OrdersResDTO convertOrderToOrderResDTO (Orders orders) {
		List<OrdersItemDTO> ordersItemDTOList = new ArrayList<>();
		long totalPrice = 0;
		for (OrdersItem ordersItem : orders.getOrdersItems()) {
			totalPrice += ordersItem.getPrice();
			ordersItemDTOList.add(new OrdersItemDTO(
					ordersItem.getName(), ordersItem.getPrice()
			));
		}
		return new OrdersResDTO(orders.getId(), totalPrice, ordersItemDTOList, orders.getOrdersStatus());
	}

}
