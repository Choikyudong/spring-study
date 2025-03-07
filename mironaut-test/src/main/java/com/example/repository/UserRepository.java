package com.example.repository;

import com.example.entity.Users;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

// micronaut.data 에서 지원하는 기능으로 Spring Data JPA와 유사하다.
@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

}
