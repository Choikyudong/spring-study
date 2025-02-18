package com.example.springjpa.dto;

import com.example.springjpa.entity.vo.PaymentInfo;

public record PaymentMethodUpdateReqDTO(int id, int customerId, String name, PaymentInfo paymentInfo) {
}
