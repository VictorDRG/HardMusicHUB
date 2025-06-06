package com.example.hardstyle_hub_backend.model.hardstyle; // Ajusta el paquete si lo nombras diferente

import jakarta.persistence.*; // Usa jakarta.persistence si Spring Boot 3+, o javax.persistence si es anterior

@Entity
@Table(name = "djs") // Nombre de la tabla en la base de datos
public class modelDJ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento del ID
    private Long id;

    @Column(nullable = false, unique = true) // Nombre artístico no puede ser nulo y debe ser único
    private String nombreArtistico;

    @Column(length = 1000) // Biografía puede ser un texto más largo
    private String biografia;

    @Column(nullable = false) // Siempre debe tener un subgénero principal
    private String subgeneroHardstyle;

    private String enlaceSpotify;
    private String enlaceSoundcloud;
    private String enlaceInstagram;
    private String imagenPerfilUrl; // URL a una imagen de perfil del DJ

    @Column(nullable = false)
    private boolean activo = true; // Por defecto, el DJ está activo

    // Constructor vacío (necesario para JPA)
    public modelDJ() {
    }

    // Constructor con campos obligatorios
    public modelDJ(String nombreArtistico, String subgeneroHardstyle) {
        this.nombreArtistico = nombreArtistico;
        this.subgeneroHardstyle = subgeneroHardstyle;
    }

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getSubgeneroHardstyle() {
        return subgeneroHardstyle;
    }

    public void setSubgeneroHardstyle(String subgeneroHardstyle) {
        this.subgeneroHardstyle = subgeneroHardstyle;
    }

    public String getEnlaceSpotify() {
        return enlaceSpotify;
    }

    public void setEnlaceSpotify(String enlaceSpotify) {
        this.enlaceSpotify = enlaceSpotify;
    }

    public String getEnlaceSoundcloud() {
        return enlaceSoundcloud;
    }

    public void setEnlaceSoundcloud(String enlaceSoundcloud) {
        this.enlaceSoundcloud = enlaceSoundcloud;
    }

    public String getEnlaceInstagram() {
        return enlaceInstagram;
    }

    public void setEnlaceInstagram(String enlaceInstagram) {
        this.enlaceInstagram = enlaceInstagram;
    }

    public String getImagenPerfilUrl() {
        return imagenPerfilUrl;
    }

    public void setImagenPerfilUrl(String imagenPerfilUrl) {
        this.imagenPerfilUrl = imagenPerfilUrl;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}