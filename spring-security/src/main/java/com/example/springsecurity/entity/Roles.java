package com.example.springsecurity.entity;

import com.example.springsecurity.domain.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Enumerated(EnumType.STRING)
	private Role role;

	public Roles(Role role) {
		this.role = role;
	}

}
