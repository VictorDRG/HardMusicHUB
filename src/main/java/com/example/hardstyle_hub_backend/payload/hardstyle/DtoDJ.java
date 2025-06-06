package com.example.hardstyle_hub_backend.payload.hardstyle; // Ajusta el paquete

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DtoDJ {

    private Long id; // Incluir para PUT (actualización)

    @NotBlank(message = "El nombre artístico no puede estar vacío")
    @Size(max = 100, message = "El nombre artístico no puede exceder 100 caracteres")
    private String nombreArtistico;

    private String biografia;

    @NotBlank(message = "El subgénero Hardstyle es obligatorio")
    private String subgeneroHardstyle;

    private String enlaceSpotify;
    private String enlaceSoundcloud;
    private String enlaceInstagram;
    private String imagenPerfilUrl;
    private boolean activo = true;

    // Constructor vacío
    public DtoDJ() {
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