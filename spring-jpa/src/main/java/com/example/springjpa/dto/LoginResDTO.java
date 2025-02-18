package com.example.springjpa.dto;

import com.example.springjpa.entity.vo.Address;

public interface LoginResDTO {

	String getNick();

	String getName();

	String getPwd();

	Address getAddress();

}
