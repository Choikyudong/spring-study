package com.example.springsecurity.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;

		Map<String, Object> userAuthority = authToken.getPrincipal().getAttributes();
		String userName;
		String email;
		String provider = authToken.getAuthorizedClientRegistrationId();
		if (provider.equals("github")) {
			userName = (String) userAuthority.get("login");
			email = Objects.requireNonNullElse((String) userAuthority.get("email"), "NonEmail");
		} else if (provider.equals("naver")) {
			Map<String, Object> res = (Map<String, Object>) userAuthority.get("response");
			userName = (String) res.get("nickname");
			email = Objects.requireNonNullElse((String) res.get("email"), "NonEmail");
		} else {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "Bad request!");
			return;
		}

		String url = "/oauth/success?userName=%s&email=%s&provider=%s";
		response.sendRedirect(String.format(
				url,
				URLEncoder.encode(userName, StandardCharsets.UTF_8),
				URLEncoder.encode(email, StandardCharsets.UTF_8),
				URLEncoder.encode(provider, StandardCharsets.UTF_8)
		));
	}


}