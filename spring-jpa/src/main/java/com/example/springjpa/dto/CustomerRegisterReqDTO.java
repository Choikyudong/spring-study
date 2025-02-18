package com.example.springjpa.dto;

import com.example.springjpa.entity.vo.Address;
import com.example.springjpa.entity.vo.UserInfo;

public record CustomerRegisterReqDTO(UserInfo userInfo, Address address) {
}

