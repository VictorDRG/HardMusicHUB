package com.example.SpringBootProject.controller.auth;

import com.example.SpringBootProject.model.Role;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.payload.auth.JwtAuthResponse;
import com.example.SpringBootProject.payload.auth.LoginDto;
import com.example.SpringBootProject.payload.auth.RegisterDto;
import com.example.SpringBootProject.repository.RoleRepository;
import com.example.SpringBootProject.repository.UserRepository;
import com.example.SpringBootProject.security.jwt.JwtTokenProvider;

import jakarta.validation.Valid; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth") // Endpoint base para autenticación
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider; // Inyecta el proveedor de JWT

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider) { 
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider; 
    }

    // Endpoint para el login de usuario y generación de JWT
    // POST http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar token
        String token = jwtTokenProvider.generateToken(authentication);

        // Devuelve el token en el cuerpo de la respuesta
        return ResponseEntity.ok(new JwtAuthResponse(token, "Bearer")); 
    }

    // Endpoint para el registro de nuevos usuarios
    // POST http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        // Verificar si el nombre de usuario ya existe
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Nombre de usuario ya existe!", HttpStatus.BAD_REQUEST);
        }

        // Verificar si el email ya existe
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email ya existe!", HttpStatus.BAD_REQUEST);
        }

        // Crear nuevo usuario
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword())); // Codificar la contraseña

        // Asignar rol por defecto (ej. ROLE_USER)
        Role roles = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role 'ROLE_USER' no encontrado."));
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user); // Guardar el usuario en la base de datos

        return new ResponseEntity<>("Usuario registrado exitosamente!", HttpStatus.OK);
    }
}