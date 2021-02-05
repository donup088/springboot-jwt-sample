package com.token.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    private String access_token;

    public TokenDto(String access_token) {
        this.access_token = access_token;
    }
}