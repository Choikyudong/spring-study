package com.example.springjpa.repository;

import com.example.springjpa.dto.CustomerLoginResDTO;
import com.example.springjpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	boolean existsByUserInfoNick(String nick);

	Optional<CustomerLoginResDTO> findByUserInfoNick(String nick);

}
