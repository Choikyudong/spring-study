package com.example.springjpa.dto;

import lombok.NonNull;

public record LoginReqDTO(@NonNull String nick, @NonNull String pwd) {

}
