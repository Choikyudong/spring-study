package com.example.springwebflux.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Table(name = "chat_messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

	@Id
	private Long id;

	private Long roomId;

	private Long userId;

	private String content;

	private LocalDateTime sendAt;

	public ChatMessage(Long id, Long userId, Long roomId, String content) {
		this.id = id;
		this.userId = userId;
		this.roomId = roomId;
		this.content = content;
		this.sendAt = LocalDateTime.now();
	}

}
