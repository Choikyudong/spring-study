package com.example.gatewayserver.rotuer;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {


	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("user-service", r -> r.path("/api/users/**")
						.uri("http://localhost:8001")
				)
				.route("board-service", r -> r.path("/api/board/**")
						.uri("http://localhost:8002")
				)
				.build();
	}

}
