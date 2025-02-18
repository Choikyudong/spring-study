package com.example.springjpa.dto;

import com.example.springjpa.entity.vo.Address;

public interface CustomerLoginResDTO {

	String getNick();

	String getName();

	String getPwd();

	Address getAddress();

}
