package com.example.springjpa.service;

import com.example.springjpa.dto.CustomerLoginReqDTO;
import com.example.springjpa.dto.CustomerLoginResDTO;
import com.example.springjpa.dto.CustomerRegisterReqDTO;
import com.example.springjpa.dto.CustomerUpdateReqDTO;
import com.example.springjpa.entity.vo.Address;
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

	public void register(CustomerRegisterReqDTO customerRegisterReqDTO) {
		Customer customer = Customer.builder()
				.name(customerRegisterReqDTO.name())
				.nick(customerRegisterReqDTO.nick())
				.pwd(customerRegisterReqDTO.pwd())
				.address(Address.builder()
					.city(customerRegisterReqDTO.city())
					.street(customerRegisterReqDTO.street())
					.build())
				.build();
		if (customerRepository.existsByNick(customer.getNick())) {
			throw new IllegalArgumentException();
		}
		customerRepository.save(customer);
	}

	public CustomerLoginResDTO login(CustomerLoginReqDTO customerLoginReqDTO) throws BadRequestException {
		CustomerLoginResDTO customerLoginResDTO = customerRepository.findByNick(customerLoginReqDTO.nick())
				.orElseThrow(IllegalArgumentException::new);
		if (!customerLoginResDTO.getPwd().equals(customerLoginReqDTO.pwd())) {
			throw new BadRequestException();
		}
		return customerLoginResDTO;
	}

	@Transactional
	public void update(CustomerUpdateReqDTO customerUpdateReqDTO) {
		Customer customer = customerRepository.findById(customerUpdateReqDTO.id())
				.orElseThrow(IllegalArgumentException::new);
		if (Objects.nonNull(customerUpdateReqDTO.name())) {
			customer.setName(customerUpdateReqDTO.name());
		}
		if (Objects.nonNull(customerUpdateReqDTO.pwd())) {
			customer.setPwd(customerUpdateReqDTO.pwd());
		}
		if (Objects.nonNull(customerUpdateReqDTO.address())) {
			customer.setAddress(customerUpdateReqDTO.address());
		}
	}

}
