package com.example.springjpa.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record Paging(int pageNumber, int pageSize, Sort sortType) {
	public Pageable toPageable() {
		return PageRequest.of(
				pageNumber,
				pageSize,
				sortType == null ? Sort.unsorted() : sortType
		);
	}
}
