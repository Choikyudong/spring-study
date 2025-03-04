package com.example.springwebflux.router;

import com.example.springwebflux.dto.ChatMessageDTO;
import com.example.springwebflux.dto.ChatSendDTO;
import com.example.springwebflux.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChatRouter {

	private final ChatService chatService;
	private static final String DEFAULT_CHAT_URI = "/chat";

	@Bean
	public RouterFunction<ServerResponse> chatRoutes() {
		return route()
				.GET(DEFAULT_CHAT_URI + "/receive/{roomId}/stream", request -> {
						Long roomId = Long.valueOf(request.pathVariable("roomId"));
						Flux<ChatMessageDTO> messageStream = chatService.streamMessage(roomId)
								.map(ChatMessageDTO::from);
						return ServerResponse.ok()
								.contentType(MediaType.TEXT_EVENT_STREAM)
								.body(messageStream, ChatMessageDTO.class);
					}
				)
				.POST(DEFAULT_CHAT_URI + "/send", request ->
						request.bodyToMono(ChatSendDTO.class)
								.flatMap(chatService::sendMessage)
								.map(ChatMessageDTO::from)
								.flatMap(dto -> ServerResponse.ok().bodyValue(dto))
								.onErrorResume(e -> {
									log.error("메시지 전송 에러 발생 : {}", e.getMessage());
									return ServerResponse.badRequest().bodyValue("Error 발생 : " + e.getMessage());
								})
				)
				.build();
	}

}
