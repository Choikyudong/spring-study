package com.example.springwebflux.repository;

import com.example.springwebflux.entity.ChatMessage;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatRepository extends R2dbcRepository<ChatMessage, Long> {

	Flux<ChatMessage> findByRoomId(Long roomId);

}
