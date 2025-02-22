package com.example.springsecurity.service;

import com.example.springsecurity.entity.Users;
import com.example.springsecurity.respoitory.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final UsersRepository usersRepository;

	@Transactional
	public void deactivate(int id) {
		Users users = usersRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found"));

		if (!users.isEnabled()) {
			throw new DisabledException("This Users is already Disabled");
		}

		users.setEnabled(false);
	}

}
