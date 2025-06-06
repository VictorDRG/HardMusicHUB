package com.example.hardstyle_hub_backend.controller.hardstyle; // Ajusta el paquete

import com.example.hardstyle_hub_backend.model.hardstyle.modelDJ;
import com.example.hardstyle_hub_backend.payload.hardstyle.DtoDJ;
import com.example.hardstyle_hub_backend.repository.hardstyle.RepositoryDJ;
import jakarta.validation.Valid; // O javax.validation.Valid si usas una versión anterior
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/djs") // Ruta base para todos los endpoints de DJs
public class DJController {

    private RepositoryDJ djRepository; // Inyectaremos el repositorio

    // Constructor para inyección de dependencias
    public DJController(RepositoryDJ djRepository) {
        this.djRepository = djRepository;
    }

    // ----------------------------------------------------------------------
    // 1. GET ALL DJs
    // Endpoint: GET http://localhost:8080/api/djs
    // Permite a cualquier usuario (incluso no autenticado) ver la lista de DJs
    // ----------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<DtoDJ>> getAllDJs() {
        List<DJ> djs = djRepository.findAll(); // Obtiene todos los DJs de la DB
        // Mapea las entidades DJ a DTOs para la respuesta
        List<DtoDJ> djDtos = djs.stream().map(this::mapToDto).collect(Collectors.toList());
        return new ResponseEntity<>(djDtos, HttpStatus.OK);
    }

    // ----------------------------------------------------------------------
    // 2. GET DJ by ID
    // Endpoint: GET http://localhost:8080/api/djs/{id}
    // Permite a cualquier usuario (incluso no autenticado) ver un DJ específico
    // ----------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<DtoDJ> getDJById(@PathVariable(name = "id") Long id) {
        DJ dj = djRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DJ no encontrado con id: " + id)); // Manejo básico de no encontrado
        return new ResponseEntity<>(mapToDto(dj), HttpStatus.OK);
    }

    // ----------------------------------------------------------------------
    // 3. CREATE DJ
    // Endpoint: POST http://localhost:8080/api/djs
    // Solo usuarios con ROLE_ADMIN pueden crear nuevos DJs
    // @PreAuthorize("hasRole('ADMIN')")
    // ----------------------------------------------------------------------
    @PreAuthorize("hasRole('ADMIN')") // Protege este endpoint
    @PostMapping
    public ResponseEntity<DtoDJ> createDJ(@Valid @RequestBody DtoDJ djDto) {
        // Verifica si ya existe un DJ con el mismo nombre artístico
        if (djRepository.findByNombreArtistico(djDto.getNombreArtistico()).isPresent()) {
            // Puedes usar un HttpStatus.CONFLICT (409) o BAD_REQUEST (400)
            return new ResponseEntity<>(null, HttpStatus.CONFLICT); // O lanzar una excepción personalizada
        }

        DJ dj = mapToEntity(djDto); // Convierte el DTO a entidad
        DJ newDJ = djRepository.save(dj); // Guarda en la base de datos

        return new ResponseEntity<>(mapToDto(newDJ), HttpStatus.CREATED); // Devuelve el DJ creado con 201 Created
    }

    // ----------------------------------------------------------------------
    // 4. UPDATE DJ
    // Endpoint: PUT http://localhost:8080/api/djs/{id}
    // Solo usuarios con ROLE_ADMIN pueden actualizar DJs
    // @PreAuthorize("hasRole('ADMIN')")
    // ----------------------------------------------------------------------
    @PreAuthorize("hasRole('ADMIN')") // Protege este endpoint
    @PutMapping("/{id}")
    public ResponseEntity<DtoDJ> updateDJ(@PathVariable(name = "id") Long id, @Valid @RequestBody DtoDJ djDto) {
        DJ dj = djRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DJ no encontrado con id: " + id)); // Manejo básico de no encontrado

        // Actualiza los campos del DJ existente con los datos del DTO
        dj.setNombreArtistico(djDto.getNombreArtistico());
        dj.setBiografia(djDto.getBiografia());
        dj.setSubgeneroHardstyle(djDto.getSubgeneroHardstyle());
        dj.setEnlaceSpotify(djDto.getEnlaceSpotify());
        dj.setEnlaceSoundcloud(djDto.getEnlaceSoundcloud());
        dj.setEnlaceInstagram(djDto.getEnlaceInstagram());
        dj.setImagenPerfilUrl(djDto.getImagenPerfilUrl());
        dj.setActivo(djDto.isActivo());

        DJ updatedDJ = djRepository.save(dj); // Guarda los cambios

        return new ResponseEntity<>(mapToDto(updatedDJ), HttpStatus.OK);
    }

    // ----------------------------------------------------------------------
    // 5. DELETE DJ
    // Endpoint: DELETE http://localhost:8080/api/djs/{id}
    // Solo usuarios con ROLE_ADMIN pueden eliminar DJs
    // @PreAuthorize("hasRole('ADMIN')")
    // ----------------------------------------------------------------------
    @PreAuthorize("hasRole('ADMIN')") // Protege este endpoint
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDJ(@PathVariable(name = "id") Long id) {
        DJ dj = djRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DJ no encontrado con id: " + id));

        djRepository.delete(dj); // Elimina el DJ

        return new ResponseEntity<>("DJ eliminado exitosamente.", HttpStatus.OK);
    }


    // ----------------------------------------------------------------------
    // Métodos de Mapeo (DJ -> DJDto y DJDto -> DJ)
    // Pueden estar en una clase de servicio o utilidades para proyectos más grandes
    // ----------------------------------------------------------------------
    private DtoDJ mapToDto(DJ dj) {
        DtoDJ djDto = new DtoDJ();
        djDto.setId(dj.getId());
        djDto.setNombreArtistico(dj.getNombreArtistico());
        djDto.setBiografia(dj.getBiografia());
        djDto.setSubgeneroHardstyle(dj.getSubgeneroHardstyle());
        djDto.setEnlaceSpotify(dj.getEnlaceSpotify());
        djDto.setEnlaceSoundcloud(dj.getEnlaceSoundcloud());
        djDto.setEnlaceInstagram(dj.getEnlaceInstagram());
        djDto.setImagenPerfilUrl(dj.getImagenPerfilUrl());
        djDto.setActivo(dj.isActivo());
        return djDto;
    }

    private DJ mapToEntity(DtoDJ djDto) {
        DJ dj = new DJ();
        // No seteamos el ID aquí, ya que la base de datos lo genera en POST
        // En PUT, el ID viene en el @PathVariable, no en el DTO para mapeo a entidad
        dj.setNombreArtistico(djDto.getNombreArtistico());
        dj.setBiografia(djDto.getBiografia());
        dj.setSubgeneroHardstyle(djDto.getSubgeneroHardstyle());
        dj.setEnlaceSpotify(djDto.getEnlaceSpotify());
        dj.setEnlaceSoundcloud(djDto.getEnlaceSoundcloud());
        dj.setEnlaceInstagram(djDto.getEnlaceInstagram());
        dj.setImagenPerfilUrl(djDto.getImagenPerfilUrl());
        dj.setActivo(djDto.isActivo());
        return dj;
    }
}