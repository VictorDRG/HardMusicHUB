package com.example.SpringBootProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank; // Importación para @NotBlank
import jakarta.validation.constraints.PositiveOrZero; // Importación para @PositiveOrZero
import jakarta.validation.constraints.Size; // Importación para @Size

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @PositiveOrZero(message = "El precio no puede ser negativo")
    private double precio;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;
}