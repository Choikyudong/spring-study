package com.example.springjpa.dto;

import lombok.NonNull;

public record OrdersListReqDTO(int customerId, @NonNull Paging paging) {
}
