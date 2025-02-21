package com.example.springsecurity.service;

import com.example.springsecurity.entity.Users;
import com.example.springsecurity.vo.JwtToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TokenService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expired}")
	private long expiredAt;

	public String createToken(Users users) {
		Objects.requireNonNull(users);

		Map<String, Object> info = new HashMap<>();
		info.put(JwsHeader.ALGORITHM, SignatureAlgorithm.HS512.getValue());
		info.put(JwsHeader.TYPE, JwsHeader.JWT_TYPE);

		Claims claims = Jwts.claims().setSubject(String.valueOf(users.getUsername()));
		claims.put("email", users.getEmail());

		List<String> roleList = users.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.toList();
		claims.put("roles", roleList);

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

	public boolean validToken(String token) throws JwtException {
		Jws<Claims> claimsJwt = Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token);

		Date expired = claimsJwt.getBody().getExpiration();
		if (expired.before(new Date())) {
			throw new ExpiredJwtException(
					claimsJwt.getHeader(),
					claimsJwt.getBody(),
					"Token Expired"
			);
		}
		return true;
	}

	public JwtToken parseToken(String jwt) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(jwt)
				.getBody();

		// 타입변환
		List<?> roles = (List<?>) claims.get("roles");
		List<GrantedAuthority> roleList = roles.stream()
				.map(role -> new SimpleGrantedAuthority((String) role))
				.collect(Collectors.toList());

		return new JwtToken (
				claims.getSubject(),
				(String) claims.get("email"),
				roleList
		);
	}

}
