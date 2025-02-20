package com.example.springsecurity.respoitory;

import com.example.springsecurity.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

	Optional<Users> findByUserName(String userName);

}
