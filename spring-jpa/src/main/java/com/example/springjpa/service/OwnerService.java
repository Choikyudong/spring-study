package com.example.springjpa.service;

import com.example.springjpa.dto.*;
import com.example.springjpa.entity.Owner;
import com.example.springjpa.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerService {

	private final OwnerRepository ownerRepository;

	@Transactional
	public void register(OwnerRegisterReqDTO reqDTO) {
		if (ownerRepository.existsByUserInfoNick(reqDTO.userInfo().getNick())) {
			throw new IllegalArgumentException("Already exists User");
		}

		Owner owner = Owner.builder()
				.userInfo(reqDTO.userInfo())
				.address(reqDTO.address())
				.build();
		ownerRepository.save(owner);
	}

	public OwnerLoginResDTO login(OwnerLoginReqDTO ownerLoginReqDTO) {
		OwnerLoginResDTO ownerLoginResDTO = ownerRepository.findByUserInfoNick(ownerLoginReqDTO.nick())
				.orElseThrow(IllegalArgumentException::new);
		if (!ownerLoginReqDTO.pwd().equals(ownerLoginResDTO.getUserInfo().getPwd())) {
			throw new IllegalArgumentException();
		}
		return ownerLoginResDTO;
	}

	@Transactional
	public void update(OwnerUpdateReqDTO updateReqDTO) {
		Owner owner = ownerRepository.findOwnerById(updateReqDTO.id())
				.orElseThrow(IllegalArgumentException::new);
		owner.updateUserInfo(updateReqDTO.userInfo());
		owner.updateAddress(updateReqDTO.address());
	}

	public void delete(int id) {
		if (!ownerRepository.existsById(id)) {
			throw new IllegalArgumentException("Not Exists User");
		}
		ownerRepository.deleteById(id);
	}

}
