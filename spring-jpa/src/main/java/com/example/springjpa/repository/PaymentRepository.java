package com.example.springjpa.repository;

import com.example.springjpa.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	Payment findByOrdersId(int orderId);

}
