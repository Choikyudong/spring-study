package com.example.springsecurity.dto;

import com.example.springsecurity.entity.Users;

public record OAuth2ResDTO(String userName, String email, String token) {

	public static OAuth2ResDTO convert(Users users, String token) {
		return new OAuth2ResDTO(
				users.getUsername(),
				users.getEmail(),
				token
		);
	}

}
