package com.example.springjpa.entity;

import com.example.springjpa.entity.vo.PaymentInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class PaymentMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int customerId;

	private String name;

	@Embedded
	private PaymentInfo paymentInfo;

	public void setName(String name) {
		this.name = name;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

}
