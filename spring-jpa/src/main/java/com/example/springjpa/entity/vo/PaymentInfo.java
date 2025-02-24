package com.example.springjpa.entity.vo;

import com.example.springjpa.domain.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Embeddable
public class PaymentInfo {

	@Enumerated(EnumType.STRING)
	private final PaymentType paymentType;

	@Column(nullable = false)
	private final String paymentDetail;

	protected PaymentInfo() {
		this.paymentType = null;
		this.paymentDetail = null;
	}

	@Builder(toBuilder = true)
	public PaymentInfo(PaymentType paymentType, String paymentDetail) {
		this.paymentType = paymentType;
		this.paymentDetail = paymentDetail;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PaymentInfo that = (PaymentInfo) o;
		return paymentType == that.paymentType && Objects.equals(paymentDetail, that.paymentDetail);
	}

	@Override
	public int hashCode() {
		return Objects.hash(paymentType, paymentDetail);
	}

}
