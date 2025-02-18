package com.example.springjpa.entity;

import com.example.springjpa.domain.Category;
import com.example.springjpa.entity.vo.Address;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurants {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToMany
	private List<RestaurantsMenu> restaurantsMenus = new ArrayList<>();

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	private Category category;

}
