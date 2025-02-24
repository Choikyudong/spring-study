package com.example.springjpa.entity;

import com.example.springjpa.entity.vo.Address;
import com.example.springjpa.entity.vo.UserInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner extends User {

	@OneToOne(cascade = CascadeType.PERSIST)
	private Restaurants restaurants;

	@Builder
	public Owner(UserInfo userInfo, Address address) {
		super.userInfo = userInfo;
		super.address = address;
	}

	@Override
	public void updateUserInfo(UserInfo info) {
		super.updateUserInfo(info);
	}

	@Override
	public void updateAddress(Address address) {
		super.updateAddress(address);
	}

}
