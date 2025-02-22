package com.example.springsecurity.controller;

import com.example.springsecurity.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping
	public String getTest() {
		return "getTest";
	}

	@PatchMapping("/user/{id}/deactivate")
	public ResponseEntity<String> deactivate(@PathVariable int id) {
		adminService.deactivate(id);
		return ResponseEntity.accepted().build();
	}

}
