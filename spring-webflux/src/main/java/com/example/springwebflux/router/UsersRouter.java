package com.example.springwebflux.router;

import com.example.springwebflux.dto.UserDTO;
import com.example.springwebflux.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class UsersRouter {

	private final UsersService usersService;
	private static final String DEFAULT_USER_URI = "/users";

	@Bean
	public RouterFunction<ServerResponse> userRoutes() {
		return route()
				.POST(DEFAULT_USER_URI + "/login", request ->
					request.bodyToMono(UserDTO.class)
							.flatMap(usersService::login)
							.flatMap(user -> ServerResponse.ok().bodyValue(user))
							.switchIfEmpty(ServerResponse.notFound().build())
				)
				.POST(DEFAULT_USER_URI + "/join", request ->
						request.bodyToMono(UserDTO.class)
								.flatMap(usersService::join)
								.flatMap(result -> ServerResponse.ok().bodyValue(result))
								.switchIfEmpty(ServerResponse.badRequest().build())
				)
				.build();
	}

}
