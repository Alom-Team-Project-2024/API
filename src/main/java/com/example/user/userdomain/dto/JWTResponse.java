package com.example.user.userdomain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * JWT DTO
 */

@Getter
@AllArgsConstructor
public class JWTResponse {
    private String accessToken;
}
