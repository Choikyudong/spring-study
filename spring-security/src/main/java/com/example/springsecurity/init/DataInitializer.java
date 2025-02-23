package com.example.springsecurity.init;

import com.example.springsecurity.domain.Role;
import com.example.springsecurity.entity.Roles;
import com.example.springsecurity.respoitory.RolesRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * 프로그램 시작시 필수 데이터를 세팅한다.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer {

	private final RolesRepository rolesRepository;

	@PostConstruct
	public void init() {
		if (rolesRepository.count() == 0) {
			List<Roles> roles = new ArrayList<>();
			roles.add(new Roles(Role.USER));
			roles.add(new Roles(Role.OAUTH2_USER));
			roles.add(new Roles(Role.ADMIN));
			rolesRepository.saveAll(roles);
		}
	}
	
}
