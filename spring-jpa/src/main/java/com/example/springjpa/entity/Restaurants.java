package com.example.springjpa.entity;

import com.example.springjpa.domain.Category;
import com.example.springjpa.entity.vo.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Restaurants {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToMany(
			mappedBy = "restaurants",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<RestaurantsMenu> restaurantsMenus = new ArrayList<>();

	@Embedded
	@Column(nullable = false)
	private Address address;

	@Enumerated(EnumType.STRING)
	private Category category;

}
