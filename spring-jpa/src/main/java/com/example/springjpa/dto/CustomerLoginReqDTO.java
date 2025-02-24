package com.example.springjpa.dto;

import org.springframework.util.StringUtils;

public record CustomerLoginReqDTO(String nick, String pwd) {

	public CustomerLoginReqDTO {
		if (!StringUtils.hasText(nick)) {
			throw new NullPointerException("login request must have nick");
		}
		if (!StringUtils.hasText(pwd)) {
			throw new NullPointerException("login request must have pwd");
		}
	}

}
