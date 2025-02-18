package com.example.springjpa.service;

import com.example.springjpa.dto.*;
import com.example.springjpa.entity.Owner;
import com.example.springjpa.entity.Restaurants;
import com.example.springjpa.entity.vo.UserInfo;
import com.example.springjpa.repository.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class OwnerService {

	private final OwnerRepository ownerRepository;

	@Transactional
	public void register(OwnerRegisterReqDTO ownerRegisterReqDTO) {
		RestaurantsRegisterReqDTO registerReqDTO = ownerRegisterReqDTO.registerReqDTO();
		Restaurants restaurants;
		if (Objects.nonNull(registerReqDTO.menus())) {
			restaurants = Restaurants.builder()
					.restaurantsMenus(registerReqDTO.menus())
					.address(registerReqDTO.address())
					.category(ownerRegisterReqDTO.registerReqDTO().category())
					.build();
		} else {
			restaurants = Restaurants.builder()
					.address(registerReqDTO.address())
					.category(ownerRegisterReqDTO.registerReqDTO().category())
					.build();
		}

		Owner owner = Owner.builder()
				.userInfo(ownerRegisterReqDTO.userInfo())
				.restaurants(restaurants)
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
	public void update(OwnerUpdateReqDTO ownerUpdateReqDTO) {
		Owner owner = ownerRepository.findById(ownerUpdateReqDTO.id())
				.orElseThrow(IllegalArgumentException::new);
		UserInfo userInfo = UserInfo.builder()
				.name(ownerUpdateReqDTO.name())
				.name(ownerUpdateReqDTO.pwd())
				.build();
		owner.setUserInfo(userInfo);
	}

	public boolean delete(int id) {
		if (!ownerRepository.existsById(id)) {
			return false;
		}
		ownerRepository.deleteById(id);
		return true;
	}

}
