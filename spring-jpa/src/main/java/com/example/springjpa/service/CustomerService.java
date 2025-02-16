package com.example.springjpa.service;

import com.example.springjpa.dto.LoginReqDTO;
import com.example.springjpa.dto.LoginResDTO;
import com.example.springjpa.dto.RegisterReqDTO;
import com.example.springjpa.dto.UpdateReqDTO;
import com.example.springjpa.entity.Address;
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

	public void register(RegisterReqDTO registerReqDTO) {
		Customer customer = Customer.builder()
				.name(registerReqDTO.name())
				.nick(registerReqDTO.nick())
				.pwd(registerReqDTO.pwd())
				.address(Address.builder()
					.city(registerReqDTO.city())
					.street(registerReqDTO.street())
					.build())
				.build();
		if (customerRepository.existsByNick(customer.getNick())) {
			throw new IllegalArgumentException();
		}
		customerRepository.save(customer);
	}

	public LoginResDTO login(LoginReqDTO loginReqDTO) throws BadRequestException {
		LoginResDTO loginResDTO = customerRepository.findByNick(loginReqDTO.nick())
				.orElseThrow(IllegalArgumentException::new);
		if (!loginResDTO.getPwd().equals(loginReqDTO.pwd())) {
			throw new BadRequestException();
		}
		return loginResDTO;
	}

	@Transactional
	public void update(UpdateReqDTO updateReqDTO) {
		Customer customer = customerRepository.findById(updateReqDTO.id())
				.orElseThrow(IllegalArgumentException::new);
		if (Objects.nonNull(updateReqDTO.name())) {
			customer.setName(updateReqDTO.name());
		}
		if (Objects.nonNull(updateReqDTO.pwd())) {
			customer.setPwd(updateReqDTO.pwd());
		}
		if (Objects.nonNull(updateReqDTO.address())) {
			customer.setAddress(updateReqDTO.address());
		}
	}

}
