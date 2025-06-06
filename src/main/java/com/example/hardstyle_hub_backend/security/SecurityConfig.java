package com.example.hardstyle_hub_backend.security;

import com.example.hardstyle_hub_backend.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Nuevo


import com.example.hardstyle_hub_backend.service.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired; 

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(authorize -> authorize
                // Permitir acceso sin autenticación a los endpoints de autenticación/registro
                .requestMatchers("/api/auth/**").permitAll()
                
                .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll() // Permitir GET de productos a todos (incluso no autenticados)
                .requestMatchers(HttpMethod.POST, "/api/productos/**").hasRole("ADMIN") // Solo ADMIN puede crear
                .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("ADMIN") // Solo ADMIN puede actualizar
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("ADMIN") // Solo ADMIN puede borrar
                .requestMatchers(HttpMethod.GET, "/api/productos/paginados/**").permitAll() // Endpoint de paginación también permitido
                .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
            )
            // Configuración para la gestión de sesiones (ahora stateless)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}