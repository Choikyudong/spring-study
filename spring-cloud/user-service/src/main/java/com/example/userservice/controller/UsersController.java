package com.example.userservice.controller;

import com.example.userservice.dto.UsersDTO;
import com.example.userservice.entity.Users;
import com.example.userservice.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;

	@GetMapping
	public ResponseEntity<Users> getUser(@RequestParam Long id) {
		return ResponseEntity.ok(usersService.getUsers(id));
	}

	@PostMapping("/login")
	public ResponseEntity<Users> login(@RequestBody UsersDTO usersDTO) {
		return ResponseEntity.ok(usersService.login(usersDTO));
	}

	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestBody UsersDTO usersDTO) {
		usersService.signUp(usersDTO);
		return ResponseEntity.ok("Ok");
	}

}
