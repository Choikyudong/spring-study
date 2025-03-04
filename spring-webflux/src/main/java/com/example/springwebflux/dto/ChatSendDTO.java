package com.example.springwebflux.dto;

public record ChatSendDTO(
		Long userId,
		Long roomId,
		String content) {
}
