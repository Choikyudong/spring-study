package com.example.springjpa.entity;

import com.example.springjpa.entity.vo.UserInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Owner {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Embedded
	private UserInfo userInfo;

	@OneToOne(cascade = CascadeType.PERSIST)
	private Restaurants restaurants;

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}

}
