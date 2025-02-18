package com.example.springjpa.entity.vo;

import com.example.springjpa.domain.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentInfo {

	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	@Column(nullable = false)
	private String paymentDetail;

}
