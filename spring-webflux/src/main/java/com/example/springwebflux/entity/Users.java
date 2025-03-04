package com.example.springwebflux.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

	@Id
	private Long id;

	private String name;

	public Users(Long id, String name) {
		this.id = id;
		this.name = name;
	}

}
