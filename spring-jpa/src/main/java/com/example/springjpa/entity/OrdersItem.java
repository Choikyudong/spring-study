package com.example.springjpa.entity;

import jakarta.persistence.*;

@Entity
public class OrdersItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Orders orders;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int price;

}
