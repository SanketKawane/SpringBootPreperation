package com.Project.SpringBootPreperation.dto;

public record AuthResponse(String accessToken,
                          String refreshToken,
                          String tokenType) {
}
