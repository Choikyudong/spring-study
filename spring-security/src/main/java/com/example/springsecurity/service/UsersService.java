package com.example.springsecurity.service;

import com.example.springsecurity.dto.*;
import com.example.springsecurity.entity.Roles;
import com.example.springsecurity.entity.Users;
import com.example.springsecurity.respoitory.RolesRepository;
import com.example.springsecurity.respoitory.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

	private final UsersRepository usersRepository;
	private final RolesRepository rolesRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;

	public void signUp(UsersSignUpReqDTO reqDTO) {
		List<Roles> rolesList = reqDTO.roleList().stream()
				.map(role -> rolesRepository.findByRole(role)
						.orElseThrow(IllegalArgumentException::new))
				.toList();

		Users newUsers = Users.builder()
				.userName(reqDTO.userName())
				.password(passwordEncoder.encode(reqDTO.password()))
				.email(reqDTO.email())
				.roles(rolesList)
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.enabled(true)
				.build();
		usersRepository.save(newUsers);
	}

	public UserLoginResDTO login(UserLoginReqDTO reqDTO) {
		Users users = loadUserByUsername(reqDTO.userName());
		if (!passwordEncoder.matches(reqDTO.password(), users.getPassword())) {
			throw new BadCredentialsException("Invalid Password");
		}

		if (!users.isEnabled()) {
			throw new DisabledException("Disabled Users");
		}

		return UserLoginResDTO.convert(users, tokenService.createToken(users));
	}

	@Override
	public Users loadUserByUsername(String username) throws UsernameNotFoundException {
		return usersRepository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	public UsersUpdateResDTO update(UsersUpdateReqDTO reqDTO) {
		Users users = loadUserByUsername(reqDTO.userName());
		users.toBuilder()
				.password(passwordEncoder.encode(reqDTO.password()))
				.email(reqDTO.email())
				.build();
		return UsersUpdateResDTO.convert(users);
	}

}
