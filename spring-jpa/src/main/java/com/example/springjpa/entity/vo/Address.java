package com.example.springjpa.entity.vo;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Embeddable
public class Address {

	private final String city;

	private final String street;

	protected Address() {
		this.city = null;
		this.street = null;
	}

	@Builder(toBuilder = true)
	public Address(String city, String street) {
		this.city = city;
		this.street = street;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return Objects.equals(city, address.city) && Objects.equals(street, address.street);
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, street);
	}

}
