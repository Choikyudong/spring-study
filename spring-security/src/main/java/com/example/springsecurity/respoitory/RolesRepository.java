package com.example.springsecurity.respoitory;

import com.example.springsecurity.domain.Role;
import com.example.springsecurity.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

	Optional<Roles> findByRole(Role role);

}
