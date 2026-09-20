package com.colegio.dto;

import java.util.UUID;

/**
 * DTO para Grado.
 */
public class GradoDTO {

    private UUID id;
    private String nivel;
    private Integer anio;
    private UUID colegioId;
    private String colegioNombre;

    public GradoDTO() {
    }

    public String getNombreCompleto() {
        return anio + "° " + nivel;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public UUID getColegioId() {
        return colegioId;
    }

    public void setColegioId(UUID colegioId) {
        this.colegioId = colegioId;
    }

    public String getColegioNombre() {
        return colegioNombre;
    }

    public void setColegioNombre(String colegioNombre) {
        this.colegioNombre = colegioNombre;
    }
}

