package com.example.springjpa.dto;

import com.example.springjpa.entity.vo.Address;
import com.example.springjpa.entity.vo.UserInfo;

public record OwnerUpdateReqDTO(int id, UserInfo userInfo, Address address) {
}
