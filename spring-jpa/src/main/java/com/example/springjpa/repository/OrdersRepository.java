package com.example.springjpa.repository;

import com.example.springjpa.domain.OrdersStatus;
import com.example.springjpa.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

	@EntityGraph(attributePaths = {"ordersItems"})
	@Query("select o from Orders o where o.id = :id and o.ordersStatus = :status")
	Optional<Orders> findOrderWithItems(@Param("id") int id, @Param("status") OrdersStatus status);

	Page<Orders> findByCustomerIdOrderByOrdersTimeDesc(int customerId, Pageable pageable);

}
