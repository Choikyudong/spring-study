package com.example.springsecurity.service;

import com.example.springsecurity.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class TokenService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expired}")
	private long expiredAt;

	public String createToken(Users users) {
		Objects.requireNonNull(users);

		Map<String, Object> info = new HashMap<>();
		info.put(JwsHeader.ALGORITHM, SignatureAlgorithm.HS256.getValue());
		info.put(JwsHeader.TYPE, JwsHeader.JWT_TYPE);

		Claims claims = Jwts.claims().setSubject(String.valueOf(users.getUsername()));
		claims.put("email", users.getEmail());
		claims.put("roles", users.getAuthorities());

		LocalDateTime localDateTime = LocalDateTime.now();
		Date issuedAt = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		Date expiration = Date.from(localDateTime.plusSeconds(expiredAt).atZone(ZoneId.systemDefault()).toInstant());

		return Jwts.builder()
				.setHeader(info)
				.setClaims(claims)
				.setIssuedAt(issuedAt)
				.setExpiration(expiration)
				.signWith(getSignKey())
				.compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}



}
