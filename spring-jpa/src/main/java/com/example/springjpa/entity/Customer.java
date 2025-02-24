package com.example.springjpa.entity;

import com.example.springjpa.entity.vo.Address;
import com.example.springjpa.entity.vo.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends User {

	@OneToMany(
			mappedBy = "customer",
			fetch = FetchType.LAZY,
			orphanRemoval = true
	)
	private List<Orders> orders = new ArrayList<>();

	@Builder
	public Customer(UserInfo userInfo, Address address) {
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

	public void addOrder(Orders orders) {
		Objects.requireNonNull(orders, "Orders can`t be null");
		this.orders.add(orders);
		orders.assignCustomer(this);
	}

}
