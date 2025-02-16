package com.example.springjpa.repository;

import com.example.springjpa.entity.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantsRepository extends JpaRepository<Restaurants, Integer> {
}
