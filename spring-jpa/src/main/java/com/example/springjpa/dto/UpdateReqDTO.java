package com.example.springjpa.dto;

import com.example.springjpa.entity.Address;

public record UpdateReqDTO(int id, String name, String pwd, Address address) {
}
