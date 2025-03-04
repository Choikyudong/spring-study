package com.example.springwebflux.dto;

import com.example.springwebflux.entity.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageDTO(
		Long userId,
		String content,
		LocalDateTime sendAt) {

	public static ChatMessageDTO from(ChatMessage msg) {
		return new ChatMessageDTO(msg.getUserId(), msg.getContent(), msg.getSendAt());
	}

}
