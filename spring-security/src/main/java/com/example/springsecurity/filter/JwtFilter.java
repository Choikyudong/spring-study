package com.example.springsecurity.filter;

import com.example.springsecurity.service.TokenService;
import com.example.springsecurity.vo.JwtToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
									@NonNull HttpServletResponse response,
									@NonNull FilterChain filterChain) throws ServletException, IOException {
		try {
			final Optional<String> auth = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION));
			if (auth.isEmpty()) {
				filterChain.doFilter(request, response);
				return;
			}
			final String jwt = auth.get().substring("Bearer ".length());
			if (tokenService.validToken(jwt)) {
				JwtToken token = tokenService.parseToken(jwt);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						token.userName(), token, token.roleList()
				);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception e) {
			int statusCode;
			String message;
			if (e instanceof IndexOutOfBoundsException) {
				statusCode = HttpStatus.BAD_REQUEST.value();
				message = "Bad Request";
			} else if (e instanceof ExpiredJwtException) {
				statusCode = HttpStatus.UNAUTHORIZED.value();
				message = "Token Expired";
			} else if (e instanceof JwtException) {
				statusCode = HttpStatus.UNAUTHORIZED.value();
				message = "Unauth Users";
			} else {
				log.error(e.getMessage());
				statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
				message = "Server Error";
			}
			response.sendError(statusCode, message);
			return;
		}
		filterChain.doFilter(request, response);
	}

}
