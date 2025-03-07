package com.example.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Serdeable.Serializable // 직렬화/역직렬화가 가능하도록 설정
public class Users {

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	protected Users() {}

	public Users(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Users{id=" + id + ", name='" + name + "'}";
	}

}
