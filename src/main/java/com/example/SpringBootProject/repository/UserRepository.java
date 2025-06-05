package com.example.SpringBootProject.repository;

import com.example.SpringBootProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Método para buscar un usuario por su nombre de usuario
    Optional<User> findByUsername(String username);

    // Método para buscar un usuario por su nombre de usuario o email (útil para login)
    Optional<User> findByUsernameOrEmail(String username, String email);

    // Métodos para verificar si el nombre de usuario o email ya existen
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}