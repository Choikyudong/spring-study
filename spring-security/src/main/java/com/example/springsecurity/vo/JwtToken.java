package com.example.springsecurity.vo;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record JwtToken(String userName, String email, List<GrantedAuthority> roleList) {
}
