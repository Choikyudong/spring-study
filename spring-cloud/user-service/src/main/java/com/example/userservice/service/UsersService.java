package com.example.userservice.service;

import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Users;
import com.example.userservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

	private final UsersRepository usersRepository;

	public Users getUsers(Long id) {
		return usersRepository.findById(id)
				.orElseThrow(IllegalArgumentException::new);
	}

	public Users login(UsersDTO usersDTO) {
		return usersRepository.save(new Users(usersDTO.name()));
	}

	public void signUp(UsersDTO usersDTO) {
		usersRepository.save(new Users(usersDTO.name()));
	}

}
