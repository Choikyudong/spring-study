package com.example.springjpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantsMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Restaurants restaurants;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int price;

	public RestaurantsMenu(String name, int price) {
		this.name = name;
		this.price = price;
	}

}
