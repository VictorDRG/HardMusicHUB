package com.example.SpringBootProject.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Generalmente un nombre no debería ser nulo
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false) // El email también debe ser único
    private String email;

    @Column(nullable = false)
    private String password; // La contraseña codificada (hashed)

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Carga los roles inmediatamente con el usuario
    @JoinTable(
        name = "user_roles", // Nombre de la tabla de unión
        joinColumns = @JoinColumn(name = "user_id"), // Columna que referencia al User
        inverseJoinColumns = @JoinColumn(name = "role_id") // Columna que referencia al Role
    )
    private Set<Role> roles = new HashSet<>();
}