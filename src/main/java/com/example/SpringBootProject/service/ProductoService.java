package com.example.SpringBootProject.service;

import com.example.SpringBootProject.exception.ResourceNotFoundException; // Importa la nueva excepción
import com.example.SpringBootProject.model.Producto;
import com.example.SpringBootProject.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    
    public void eliminarProducto(Long id) {
        // Verifica si el producto existe antes de intentar eliminarlo
        Producto producto = productoRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
        productoRepository.delete(producto); 
    }

    public Page<Producto> obtenerTodosLosProductosPaginados(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productoRepository.findAll(pageable);
    }
}