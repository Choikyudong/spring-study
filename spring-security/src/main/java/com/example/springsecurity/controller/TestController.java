package com.example.springsecurity.controller;

import com.example.springsecurity.dto.UserLoginReqDTO;
import com.example.springsecurity.dto.UserLoginResDTO;
import com.example.springsecurity.dto.UsersRegisterReqDTO;
import com.example.springsecurity.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {

	private final UsersService usersService;

	@GetMapping
	public String getTest() {
		return "getTest";
	}

	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestBody UsersRegisterReqDTO reqDTO) {
		usersService.signUp(reqDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("User Created Success");
	}

	@PostMapping("/login")
	public ResponseEntity<UserLoginResDTO> login(@RequestBody UserLoginReqDTO reqDTO) {
		return ResponseEntity.ok(usersService.login(reqDTO));
	}

	@PostMapping("/secure-test")
	public ResponseEntity<String> secureTest() {
		return ResponseEntity.ok().build();
	}

}
