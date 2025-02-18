package com.example.springjpa.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor()
public class UserInfo {

	@Column(unique = true)
	private String nick;

	private String name;

	private String pwd;

	@Builder
	public UserInfo(String name, String pwd) {
		this.name = name;
		this.pwd = pwd;
	}

}
