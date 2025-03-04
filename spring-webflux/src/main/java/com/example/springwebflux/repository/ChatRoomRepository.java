package com.example.springwebflux.repository;

import com.example.springwebflux.entity.ChatRoom;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ChatRoomRepository extends R2dbcRepository<ChatRoom, Long> {

	Mono<Boolean> existsChatRoomByName(String name);

	Mono<Boolean> existsChatRoomById(Long Id);

}
