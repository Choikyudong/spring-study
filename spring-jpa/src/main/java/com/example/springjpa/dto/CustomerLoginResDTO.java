package com.example.springjpa.dto;

import com.example.springjpa.entity.vo.Address;
import com.example.springjpa.entity.vo.UserInfo;

public interface CustomerLoginResDTO {

	UserInfo getUserInfo();

	Address getAddress();

}
