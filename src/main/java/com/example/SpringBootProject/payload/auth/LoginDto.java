package com.example.SpringBootProject.payload.auth;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class LoginDto {
    @NotBlank(message = "El nombre de usuario o email no puede estar vacío")
    private String usernameOrEmail;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
}