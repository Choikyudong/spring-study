package com.example.springwebflux.service;

import com.example.springwebflux.dto.ChatSendDTO;
import com.example.springwebflux.entity.ChatMessage;
import com.example.springwebflux.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;

	private final Sinks.Many<ChatMessage> messageSink = Sinks.many().multicast().onBackpressureBuffer();

	public Mono<ChatMessage> sendMessage(ChatSendDTO sendDTO) {
		ChatMessage message = new ChatMessage(null, sendDTO.userId(), sendDTO.roomId(), sendDTO.content());
		return chatRepository.save(message)
				.doOnSuccess(messageSink::tryEmitNext);
	}

	public Flux<ChatMessage> streamMessage(Long roomId) {
		Flux<ChatMessage> initMessage = chatRepository.findByRoomId(roomId);
		Flux<ChatMessage> newMessage = messageSink.asFlux()
				.filter(msg -> msg.getRoomId().equals(roomId));
		return initMessage.concatWith(newMessage);
	}

}
