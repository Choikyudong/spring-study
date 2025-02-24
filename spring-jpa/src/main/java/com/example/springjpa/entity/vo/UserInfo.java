package com.example.springjpa.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@Getter
@Embeddable
public class UserInfo {

	@Column(unique = true)
	private final String nick;

	private final String name;

	private final String pwd;

	protected UserInfo() {
		this.nick = null;
		this.name = null;
		this.pwd = null;
	}

	@Builder(toBuilder = true)
	public UserInfo(String nick, String name, String pwd) {
		this.nick = nick;
		this.name = name;
		this.pwd = pwd;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserInfo userInfo = (UserInfo) o;
		return Objects.equals(nick, userInfo.nick) && Objects.equals(name, userInfo.name) && Objects.equals(pwd, userInfo.pwd);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nick, name, pwd);
	}

}
