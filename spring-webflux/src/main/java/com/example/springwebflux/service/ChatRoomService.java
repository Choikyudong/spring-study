package com.example.springwebflux.service;

import com.example.springwebflux.dto.ChatRoomMakeDTO;
import com.example.springwebflux.entity.ChatRoom;
import com.example.springwebflux.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomRepository chatRoomRepository;

	public Flux<ChatRoom> getRoomList() {
		return chatRoomRepository.findAll();
	}

	public Mono<Boolean> existsRoom(Long roomId) {
		return chatRoomRepository.existsChatRoomById(roomId);
	}

	public Mono<ChatRoom> getRoom(Long id) {
		return chatRoomRepository.existsById(id)
				.flatMap(exists -> {
					if (!exists) {
						return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id : " + id));
					}
					return chatRoomRepository.findById(id);
				});
	}

	public Mono<Boolean> makeRoom(ChatRoomMakeDTO makeDTO) {
		return chatRoomRepository.existsChatRoomByName(makeDTO.roomName())
				.flatMap(exists -> {
					if (exists) {
						return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 존재하는 방입니다. userName : " + makeDTO.roomName()));
					}
					ChatRoom chatRoom = new ChatRoom(null, makeDTO.roomName());
					return chatRoomRepository.save(chatRoom)
							.map(Objects::nonNull);
				});
	}

}
