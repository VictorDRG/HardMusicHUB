package com.example.SpringBootProject.payload.auth;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class RegisterDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String name;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String username;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un formato de email válido")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
}