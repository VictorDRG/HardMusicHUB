package com.example.SpringBootProject.controller;

// Asegúrate de tener las importaciones correctas para tus DTOs, etc.
import com.example.SpringBootProject.model.Producto;
import com.example.SpringBootProject.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Endpoint para obtener todos los productos
    // GET http://localhost:8080/api/productos
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Endpoint para obtener un producto por ID
    // GET http://localhost:8080/api/productos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        // El servicio ahora lanza ResourceNotFoundException si no encuentra el producto,
        // que será capturada por GlobalExceptionHandler.
        Producto producto = productoService.obtenerProductoPorId(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    // Endpoint para guardar un nuevo producto (crear o actualizar)
    // POST http://localhost:8080/api/productos
    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@Valid @RequestBody Producto producto) { // << @Valid aquí
        Producto nuevoProducto = productoService.guardarProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    // Endpoint para actualizar un producto existente
    // PUT http://localhost:8080/api/productos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) { // << @Valid aquí
        productoService.obtenerProductoPorId(id);
        producto.setId(id);
        Producto productoActualizado = productoService.guardarProducto(producto);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    // Endpoint para eliminar un producto
    // DELETE http://localhost:8080/api/productos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        // El servicio ahora se encarga de verificar la existencia y lanzar la excepción
        productoService.eliminarProducto(id);
        return new ResponseEntity<>("Producto eliminado exitosamente", HttpStatus.OK);
    }

    // Endpoint para obtener productos con paginación y ordenación
    // GET http://localhost:8080/api/productos/paginados?page=0&size=10&sortBy=nombre&sortDir=asc
    @GetMapping("/paginados")
    public ResponseEntity<Page<Producto>> obtenerTodosLosProductosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<Producto> productos = productoService.obtenerTodosLosProductosPaginados(page, size, sortBy, sortDir);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}