package com.token.dto;

import lombok.Data;

@Data
public class KakaoProfile {
    private Long id;
    private Properties properties;

    @Data
    private static class Properties {
        private String nickname;
        private String thumbnail_image;
        private String profile_image;
    }
}
