package com.example.springwebflux.service;

import com.example.springwebflux.dto.UserDTO;
import com.example.springwebflux.entity.Users;
import com.example.springwebflux.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

	private final UsersRepository usersRepository;

	public Mono<Users> login(UserDTO userDTO) {
		return usersRepository.findUsersByName(userDTO.userName())
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다. userName : " + userDTO.userName())));
	}

	public Mono<Boolean> join(UserDTO userDTO) {
		return usersRepository.existsUsersByName(userDTO.userName())
				.flatMap(exists -> {
					if (exists) {
						return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 존재하는 사용자 입니다. userName : " + userDTO.userName()));
					}
					Users users = new Users(null, userDTO.userName());
					return usersRepository.save(users)
							.map(Objects::nonNull);
				});
	}

}
