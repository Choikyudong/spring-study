package com.example.springjpa.dto;

import com.example.springjpa.domain.Category;
import com.example.springjpa.entity.RestaurantsMenu;
import com.example.springjpa.entity.vo.Address;

import java.util.List;

public record RestaurantsRegisterReqDTO(
		String name,
		List<RestaurantsMenu> menus, Address address, Category category) {
}
