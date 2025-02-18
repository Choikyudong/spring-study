package com.example.springjpa.dto;

import lombok.NonNull;

public record CustomerLoginReqDTO(@NonNull String nick, @NonNull String pwd) {

}
