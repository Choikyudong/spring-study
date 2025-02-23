package com.example.springsecurity.dto;

import com.example.springsecurity.entity.Users;

public record UserLoginResDTO(String userName, String email, String token) {

	public static UserLoginResDTO convert(Users users, String token) {
		return new UserLoginResDTO(
				users.getUsername(),
				users.getEmail(),
				token
		);
	}

}
