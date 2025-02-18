package com.example.springjpa.entity;

import com.example.springjpa.entity.vo.Address;
import com.example.springjpa.entity.vo.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Embedded
	private UserInfo userInfo;

	@Embedded
	@Column(nullable = false)
	private Address address;

	@OneToMany(mappedBy = "customer")
	private List<Orders> orders;

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
