package com.token.dto;

import lombok.Data;


@Data
public class KakaoAuth {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
}
