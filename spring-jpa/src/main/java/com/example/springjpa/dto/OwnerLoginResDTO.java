package com.example.springjpa.dto;

import com.example.springjpa.entity.Restaurants;
import com.example.springjpa.entity.vo.UserInfo;

public interface OwnerLoginResDTO {

	UserInfo getUserInfo();

	Restaurants getRestaurants();

}
