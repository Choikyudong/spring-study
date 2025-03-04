package com.example.springwebflux.controller;

import com.example.springwebflux.dto.ChatRoomMakeDTO;
import com.example.springwebflux.entity.ChatRoom;
import com.example.springwebflux.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class ChatRoomController {

	private final ChatRoomService chatRoomService;

	@GetMapping
	public Flux<ChatRoom> getRoomList() {
		return chatRoomService.getRoomList();
	}

	@GetMapping("/{id}")
	public Mono<ChatRoom> getRoom(@PathVariable Long id) {
		return chatRoomService.getRoom(id);
	}

	@GetMapping("/{id}/exists")
	public Mono<Boolean> existsRoom(@PathVariable Long id) {
		return chatRoomService.existsRoom(id);
	}

	@PostMapping
	public Mono<Boolean> makeRoom(@RequestBody ChatRoomMakeDTO makeDTO) {
		return chatRoomService.makeRoom(makeDTO);
	}

}
