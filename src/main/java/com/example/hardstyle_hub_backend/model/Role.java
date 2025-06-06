package com.example.hardstyle_hub_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // Ej: ROLE_ADMIN, ROLE_USER

    // Puedes añadir constructores específicos si lo necesitas, por ejemplo:
    public Role(String name) {
        this.name = name;
    }
}