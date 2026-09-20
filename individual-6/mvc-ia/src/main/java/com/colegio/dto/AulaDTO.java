package com.colegio.dto;

import java.util.UUID;

/**
 * DTO para Aula.
 */
public class AulaDTO {

    private UUID id;
    private String division;
    private String turno;
    private Integer capacidad;
    private UUID gradoId;
    private String gradoNombre;
    private Integer cantidadAlumnos = 0;

    public AulaDTO() {
    }

    public String getNombreCompleto() {
        return (gradoNombre != null ? gradoNombre : "Grado") + " \"" + division + "\" (" + turno + ")";
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public UUID getGradoId() {
        return gradoId;
    }

    public void setGradoId(UUID gradoId) {
        this.gradoId = gradoId;
    }

    public String getGradoNombre() {
        return gradoNombre;
    }

    public void setGradoNombre(String gradoNombre) {
        this.gradoNombre = gradoNombre;
    }

    public Integer getCantidadAlumnos() {
        return cantidadAlumnos;
    }

    public void setCantidadAlumnos(Integer cantidadAlumnos) {
        this.cantidadAlumnos = cantidadAlumnos;
    }
}

