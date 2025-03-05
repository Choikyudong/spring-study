package com.example.boardservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long userId;

	private String subject;

	private String content;

	protected Board() {}

	public Board(Long userId, String subject, String content) {
		this.userId = userId;
		this.subject = subject;
		this.content = content;
	}

}
