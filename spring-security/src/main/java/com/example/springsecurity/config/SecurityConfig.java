package com.example.springsecurity.config;

import com.example.springsecurity.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.GET).permitAll()
						.requestMatchers(HttpMethod.OPTIONS).permitAll()
						.requestMatchers(HttpMethod.POST,
								"/signUp", "/login"
						).permitAll()
						.requestMatchers(HttpMethod.PATCH).authenticated()
						.requestMatchers(HttpMethod.DELETE).authenticated()
						.anyRequest().authenticated()
				)
				.httpBasic(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
