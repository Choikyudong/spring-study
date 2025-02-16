package com.example.springjpa.repository;

import com.example.springjpa.entity.RestaurantsMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantsMenuRepository extends JpaRepository<RestaurantsMenu, Integer> {
}
