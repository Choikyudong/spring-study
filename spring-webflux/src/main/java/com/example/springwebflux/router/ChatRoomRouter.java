package com.example.springwebflux.router;

import com.example.springwebflux.dto.ChatRoomMakeDTO;
import com.example.springwebflux.entity.ChatRoom;
import com.example.springwebflux.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.Collections;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class ChatRoomRouter {

	private final ChatRoomService chatRoomService;
	private static final String DEFAULT_ROOM_URI = "/room";

	@Bean
	public RouterFunction<ServerResponse> chatRoomRoutes() {
		return route()
				.GET(DEFAULT_ROOM_URI, request ->
					chatRoomService.getRoomList()
						.collectList()
						.flatMap(rooms -> {
							if (rooms.isEmpty()) {
								return ServerResponse.ok().bodyValue(Collections.emptyList());
							}
							return ServerResponse.ok().body(Flux.fromIterable(rooms), ChatRoom.class);
						})
				)
				.GET(DEFAULT_ROOM_URI + "/{id}", request -> {
						Long id = Long.valueOf(request.pathVariable("id"));
						return chatRoomService.getRoom(id)
								.flatMap(result -> ServerResponse.ok().bodyValue(result))
								.switchIfEmpty(ServerResponse.badRequest().build());
					}
				)
				.GET(DEFAULT_ROOM_URI + "/{id}/exists", request -> {
						Long id = Long.valueOf(request.pathVariable("id"));
						return chatRoomService.existsRoom(id)
								.flatMap(result -> ServerResponse.ok().bodyValue(result))
								.switchIfEmpty(ServerResponse.badRequest().build());
					}
				)
				.POST(DEFAULT_ROOM_URI, request ->
						request.bodyToMono(ChatRoomMakeDTO.class)
								.flatMap(chatRoomService::makeRoom)
								.flatMap(result -> ServerResponse.ok().bodyValue(result))
								.switchIfEmpty(ServerResponse.badRequest().build())
				)
				.build();
	}

}
