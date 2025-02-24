package com.example.springjpa.repository;

import com.example.springjpa.dto.OwnerLoginResDTO;
import com.example.springjpa.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

	boolean existsByUserInfoNick(String nick);

	Optional<Owner> findOwnerById(int id);

	Optional<OwnerLoginResDTO> findByUserInfoNick(String nick);

}
