package com.example.springjpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(unique = true)
	private String nick;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String pwd;

	@Column(nullable = false)
	private Address address;

	@OneToMany(mappedBy = "customer")
	private List<Orders> orders;

	@OneToMany
	private List<PaymentMethod> paymentMethods;

	public void setName(String name) {
		this.name = name;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
