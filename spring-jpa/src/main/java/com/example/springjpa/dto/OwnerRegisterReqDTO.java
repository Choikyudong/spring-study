package com.example.springjpa.dto;

import com.example.springjpa.entity.vo.UserInfo;

public record OwnerRegisterReqDTO(UserInfo userInfo, RestaurantsRegisterReqDTO registerReqDTO) {
}
