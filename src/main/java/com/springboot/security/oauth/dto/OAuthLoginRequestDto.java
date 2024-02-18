package com.springboot.security.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthLoginRequestDto {
    private String accessToken;
    private String refreshToken;
}
