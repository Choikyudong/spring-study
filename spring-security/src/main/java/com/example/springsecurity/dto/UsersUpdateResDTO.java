package com.example.springsecurity.dto;

import com.example.springsecurity.entity.Users;

public record UsersUpdateResDTO(String userName, String email) {

	public static UsersUpdateResDTO convert(Users users) {
		return new UsersUpdateResDTO(
				users.getUsername(),
				users.getEmail()
		);
	}
}
