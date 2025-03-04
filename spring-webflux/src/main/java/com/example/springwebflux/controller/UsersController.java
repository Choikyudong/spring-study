package com.example.springwebflux.controller;

import com.example.springwebflux.dto.UserDTO;
import com.example.springwebflux.entity.Users;
import com.example.springwebflux.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Deprecated
@RestController
//@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;

	/*
	@PostMapping("/login")
	public Mono<Users> login(@RequestBody UserDTO userDTO) {
		return usersService.login(userDTO);
	}

	@PostMapping("/join")
	public Mono<Boolean> join(@RequestBody UserDTO userDTO) {
		return usersService.join(userDTO);
	}
	*/

}
