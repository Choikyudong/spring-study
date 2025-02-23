package com.example.springsecurity.service;

import com.example.springsecurity.domain.Role;
import com.example.springsecurity.dto.*;
import com.example.springsecurity.entity.Roles;
import com.example.springsecurity.entity.Users;
import com.example.springsecurity.respoitory.RolesRepository;
import com.example.springsecurity.respoitory.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {

	private final UsersRepository usersRepository;
	private final RolesRepository rolesRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;

	public void signUp(String userName, String email, String provider) {
		Roles roles = rolesRepository.findByRole(Role.OAUTH2_USER)
				.orElseThrow(IllegalArgumentException::new);
		List<Roles> rolesList = List.of(roles);

		Users newUsers = Users.builder()
				.userName(userName)
				.password(passwordEncoder.encode("OAUTH_USER"))
				.email(email)
				.roles(rolesList)
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.enabled(true)
				.provider(provider)
				.build();
		usersRepository.save(newUsers);
	}

	public boolean existsUser(String userName) {
		return usersRepository.existsByUserName(userName);
	}

	public OAuth2ResDTO login(String userName) {
		Users users = usersRepository.findByUserName(userName)
				.orElseThrow(IllegalArgumentException::new);

		if (!users.isEnabled()) {
			throw new DisabledException("Disabled Users");
		}

		boolean isOAuthUser = users.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(Role.OAUTH2_USER.name()));
		if (!isOAuthUser) {
			throw new BadCredentialsException("User cant login here");
		}

		return OAuth2ResDTO.convert(users, tokenService.createToken(users));
	}


}
