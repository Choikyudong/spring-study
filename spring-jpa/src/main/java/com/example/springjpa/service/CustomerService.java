package com.example.springjpa.service;

import com.example.springjpa.dto.CustomerLoginReqDTO;
import com.example.springjpa.dto.CustomerLoginResDTO;
import com.example.springjpa.dto.CustomerRegisterReqDTO;
import com.example.springjpa.dto.CustomerUpdateReqDTO;
import com.example.springjpa.entity.Customer;
import com.example.springjpa.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;

	@Transactional
	public void register(CustomerRegisterReqDTO reqDTO) {
		if (customerRepository.existsByUserInfoNick(reqDTO.userInfo().getNick())) {
			throw new IllegalArgumentException("Already exists User");
		}

		Customer customer = Customer.builder()
				.userInfo(reqDTO.userInfo())
				.address(reqDTO.address())
				.build();
		customerRepository.save(customer);
	}

	public Customer findById(int id) {
		return customerRepository.findById(id)
				.orElseThrow(IllegalArgumentException::new);
	}

	public CustomerLoginResDTO login(CustomerLoginReqDTO loginReq) throws BadRequestException {
		CustomerLoginResDTO loginRes = customerRepository.findByUserInfoNick(loginReq.nick())
				.orElseThrow(IllegalArgumentException::new);

		if (!loginRes.getUserInfo().getPwd().equals(loginReq.pwd())) {
			throw new BadRequestException();
		}

		return loginRes;
	}

	@Transactional
	public void update(CustomerUpdateReqDTO customerUpdateReqDTO) {
		Customer customer = customerRepository.findById(customerUpdateReqDTO.id())
				.orElseThrow(IllegalArgumentException::new);
		if (Objects.nonNull(customerUpdateReqDTO.userInfo())) {
			customer.updateUserInfo(customerUpdateReqDTO.userInfo());
		}
		if (Objects.nonNull(customerUpdateReqDTO.address())) {
			customer.updateAddress(customerUpdateReqDTO.address());
		}
	}

}
