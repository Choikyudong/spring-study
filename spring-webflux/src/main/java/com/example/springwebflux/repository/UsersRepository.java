package com.example.springwebflux.repository;

import com.example.springwebflux.entity.Users;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsersRepository extends R2dbcRepository<Users, String> {

	Mono<Users> findUsersByName(String name);

	Mono<Boolean> existsUsersByName(String name);

}
