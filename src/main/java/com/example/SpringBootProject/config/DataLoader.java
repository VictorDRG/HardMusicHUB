package com.example.SpringBootProject.config; // O el paquete que prefieras para la configuración

import com.example.SpringBootProject.model.Role;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.RoleRepository;
import com.example.SpringBootProject.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initDatabase(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Crea roles si no existen
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

            // Crea usuario ADMIN si no existe
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("adminpass")); 
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(adminRole);
                admin.setRoles(adminRoles);
                userRepository.save(admin);
                System.out.println("Usuario ADMIN creado.");
            }

            // Crea usuario USER si no existe
            if (!userRepository.existsByUsername("user")) {
                User normalUser = new User();
                normalUser.setUsername("user");
                normalUser.setPassword(passwordEncoder.encode("userpass")); 
                Set<Role> userRoles = new HashSet<>();
                userRoles.add(userRole);
                normalUser.setRoles(userRoles);
                userRepository.save(normalUser);
                System.out.println("Usuario USER creado.");
            }
        };
    }
}