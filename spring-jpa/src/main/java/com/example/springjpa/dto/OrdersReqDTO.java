package com.example.springjpa.dto;

import com.example.springjpa.entity.vo.PaymentInfo;

import java.util.List;

public record OrdersReqDTO(int customerId, List<OrdersItemDTO> list, PaymentInfo paymentInfo) {
}
