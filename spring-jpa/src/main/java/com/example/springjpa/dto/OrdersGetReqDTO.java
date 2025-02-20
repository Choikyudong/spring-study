package com.example.springjpa.dto;

import com.example.springjpa.domain.OrdersStatus;

public record OrdersGetReqDTO(int id, OrdersStatus ordersStatus) {
}
