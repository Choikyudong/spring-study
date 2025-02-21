package com.example.springsecurity.controller;

import com.example.springsecurity.dto.*;
import com.example.springsecurity.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

	private final UsersService usersService;

	@GetMapping
	public String getTest() {
		return "getTest";
	}

	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestBody UsersSignUpReqDTO reqDTO) {
		usersService.signUp(reqDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("User Created Success");
	}

	@PostMapping("/login")
	public ResponseEntity<UserLoginResDTO> login(@RequestBody UserLoginReqDTO reqDTO) {
		return ResponseEntity.ok(usersService.login(reqDTO));
	}

	@PatchMapping("/update")
	public ResponseEntity<UsersUpdateResDTO> login(@RequestBody UsersUpdateReqDTO reqDTO) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(usersService.update(reqDTO));
	}

}
