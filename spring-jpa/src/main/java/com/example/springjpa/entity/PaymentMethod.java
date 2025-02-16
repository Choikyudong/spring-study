package com.example.springjpa.entity;

import com.example.springjpa.domain.Payment;
import jakarta.persistence.*;

@Entity
public class PaymentMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Enumerated(EnumType.STRING)
	private Payment payment;

}
