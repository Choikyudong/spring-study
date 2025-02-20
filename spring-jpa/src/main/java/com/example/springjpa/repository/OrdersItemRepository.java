package com.example.springjpa.repository;

import com.example.springjpa.entity.OrdersItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersItemRepository extends JpaRepository<OrdersItem, Integer> {

}
