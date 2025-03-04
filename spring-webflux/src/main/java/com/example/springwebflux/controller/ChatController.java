package com.example.springwebflux.controller;

import com.example.springwebflux.dto.ChatMessageDTO;
import com.example.springwebflux.dto.ChatSendDTO;
import com.example.springwebflux.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Deprecated
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

	/*
	private final ChatService chatService;

	@GetMapping(value = "/receive/{roomId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ChatMessageDTO> receive(@PathVariable Long roomId) {
		return chatService.streamMessage(roomId)
				.map(ChatMessageDTO::from);
	}

	@PostMapping("/send")
	public Mono<ChatMessageDTO> sendMessage(@RequestBody ChatSendDTO chatSendDTO) {
		return chatService.sendMessage(chatSendDTO)
				.map(ChatMessageDTO::from);
	}
	*/

}
