package com.example.springjpa.entity;

import jakarta.persistence.*;

@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;

}
