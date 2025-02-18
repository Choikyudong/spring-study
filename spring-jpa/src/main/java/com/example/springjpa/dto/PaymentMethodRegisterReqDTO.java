package com.example.springjpa.dto;

import com.example.springjpa.entity.vo.PaymentInfo;

public record PaymentMethodRegisterReqDTO(int customerId, String name, PaymentInfo paymentInfo) {
}
