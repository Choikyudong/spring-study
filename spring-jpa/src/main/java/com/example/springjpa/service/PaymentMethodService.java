package com.example.springjpa.service;

import com.example.springjpa.dto.PaymentMethodDeleteReqDTO;
import com.example.springjpa.dto.PaymentMethodRegisterReqDTO;
import com.example.springjpa.dto.PaymentMethodUpdateReqDTO;
import com.example.springjpa.entity.Customer;
import com.example.springjpa.entity.PaymentMethod;
import com.example.springjpa.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

	private final PaymentMethodRepository paymentMethodRepository;
	private final CustomerService customerService;

	@Transactional
	public List<PaymentMethod> register(PaymentMethodRegisterReqDTO paymentMethodRegisterReqDTO) {
		Customer customer = customerService.findById(paymentMethodRegisterReqDTO.customerId());
		PaymentMethod paymentMethod = PaymentMethod.builder()
				.customer(customer)
				.name(paymentMethodRegisterReqDTO.name())
				.paymentInfo(paymentMethodRegisterReqDTO.paymentInfo())
				.build();
		paymentMethodRepository.save(paymentMethod);
		return getList(paymentMethodRegisterReqDTO.customerId());
	}

	public List<PaymentMethod> getList(int customerId) {
		return paymentMethodRepository.findByCustomerId(customerId);
	}

	@Transactional
	public List<PaymentMethod> update(PaymentMethodUpdateReqDTO paymentmethodUpdateReqDTO) {
		PaymentMethod paymentMethod = paymentMethodRepository
				.findByIdAndCustomerId(paymentmethodUpdateReqDTO.id(), paymentmethodUpdateReqDTO.customerId())
				.orElseThrow(IllegalArgumentException::new);
		if (Objects.nonNull(paymentmethodUpdateReqDTO.name())) {
			paymentMethod.setName(paymentmethodUpdateReqDTO.name());
		}
		if (Objects.nonNull(paymentmethodUpdateReqDTO.paymentInfo())) {
			paymentMethod.setPaymentInfo(paymentmethodUpdateReqDTO.paymentInfo());
		}
		return getList(paymentmethodUpdateReqDTO.customerId());
	}

	@Transactional
	public boolean delete(PaymentMethodDeleteReqDTO paymentMethodDeleteReqDTO) {
		if (!paymentMethodRepository.existsByIdAndCustomerId(paymentMethodDeleteReqDTO.id(), paymentMethodDeleteReqDTO.cutsomerId())) {
			return false;
		}
		paymentMethodRepository.deleteByIdAndCustomerId(
			paymentMethodDeleteReqDTO.id(),
			paymentMethodDeleteReqDTO.cutsomerId()
		);
		return true;
	}

}
