package com.example.springsecurity.controller;

import com.example.springsecurity.dto.OAuth2ResDTO;
import com.example.springsecurity.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

	private final OAuthService oAuthService;

	@GetMapping("/success")
	public ResponseEntity<OAuth2ResDTO> oauthSuccess(
			@RequestParam String userName,
			@RequestParam String email,
			@RequestParam String provider) {
		if (!oAuthService.existsUser(userName)) {
			oAuthService.signUp(userName, email, provider);
		}
		return ResponseEntity.ok(oAuthService.login(userName));
	}

	@GetMapping("/fail")
	public String oauthFail() {
		return "oauthFail";
	}

}
