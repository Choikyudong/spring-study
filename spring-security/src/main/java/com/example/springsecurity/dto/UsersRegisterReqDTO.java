package com.example.springsecurity.dto;

import com.example.springsecurity.domain.Role;

import java.util.List;

public record UsersRegisterReqDTO(String userName, String password, String email, List<Role> roleList) {
	public UsersRegisterReqDTO {
		if (roleList == null) {
			roleList = List.of(Role.USER);
		}
	}
}
