package com.example.springjpa.entity.vo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor()
public class Address {

	private String city;

	private String street;

}
