package com.example.hardstyle_hub_backend.repository;

import com.example.hardstyle_hub_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name); // Método para buscar un rol por su nombre
}