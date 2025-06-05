package com.example.SpringBootProject.repository;

import com.example.SpringBootProject.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta interfaz es un componente de Spring para la capa de persistencia
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}