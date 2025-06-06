package com.example.hardstyle_hub_backend.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer"; // Por defecto, el tipo de token es "Bearer"

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer"; // Asegura que se establece el valor por defecto
    }
}