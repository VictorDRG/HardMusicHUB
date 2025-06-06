package com.example.hardstyle_hub_backend.repository.hardstyle; // Ajusta el paquete

import com.example.hardstyle_hub_backend.model.hardstyle.modelDJ; // Importa tu entidad DJ
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RepositoryDJ extends JpaRepository<modelDJ, Long> {
    // Puedes añadir métodos personalizados aquí si necesitas buscar DJs por nombre, etc.
    Optional<modelDJ> findByNombreArtistico(String nombreArtistico);
}