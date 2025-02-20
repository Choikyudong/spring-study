package com.example.springjpa.service;

import com.example.springjpa.dto.CustomerLoginReqDTO;
import com.example.springjpa.dto.CustomerLoginResDTO;
import com.example.springjpa.dto.CustomerRegisterReqDTO;
import com.example.springjpa.dto.CustomerUpdateReqDTO;
import com.example.springjpa.entity.Customer;
import com.example.springjpa.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;

	public Customer findCustomer(int customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(IllegalArgumentException::new);
	}

	public void register(CustomerRegisterReqDTO customerRegisterReqDTO) {
		Customer customer = Customer.builder()
				.userInfo(customerRegisterReqDTO.userInfo())
				.address(customerRegisterReqDTO.address())
				.build();
		if (customerRepository.existsByUserInfoNick(customer.getUserInfo().getNick())) {
			throw new IllegalArgumentException();
		}
		customerRepository.save(customer);
	}

	public CustomerLoginResDTO login(CustomerLoginReqDTO customerLoginReqDTO) throws BadRequestException {
		CustomerLoginResDTO customerLoginResDTO = customerRepository.findByUserInfoNick(customerLoginReqDTO.nick())
				.orElseThrow(IllegalArgumentException::new);
		if (!customerLoginResDTO.getUserInfo().getPwd().equals(customerLoginReqDTO.pwd())) {
			throw new BadRequestException();
		}
		return customerLoginResDTO;
	}

	@Transactional
	public void update(CustomerUpdateReqDTO customerUpdateReqDTO) {
		Customer customer = customerRepository.findById(customerUpdateReqDTO.id())
				.orElseThrow(IllegalArgumentException::new);
		if (Objects.nonNull(customerUpdateReqDTO.userInfo())) {
			customer.setUserInfo(customerUpdateReqDTO.userInfo());
		}
		if (Objects.nonNull(customerUpdateReqDTO.address())) {
			customer.setAddress(customerUpdateReqDTO.address());
		}
	}

}
