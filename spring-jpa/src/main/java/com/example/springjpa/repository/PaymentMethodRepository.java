package com.example.springjpa.repository;

import com.example.springjpa.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

	List<PaymentMethod> findByCustomerId(int customerId);

	Optional<PaymentMethod> findByIdAndCustomerId(int id, int customerId);

	boolean existsByIdAndCustomerId(int id, int customerId);

	void deleteByIdAndCustomerId(int id, int customerId);

}
