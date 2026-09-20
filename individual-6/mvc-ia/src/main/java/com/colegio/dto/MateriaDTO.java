package com.colegio.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DTO para la gestión y presentación de Materias.
 */
public class MateriaDTO {

    private UUID id;

    @NotBlank(message = "El nombre de la materia es obligatorio")
    private String nombre;

    @NotNull(message = "Las horas semanales son obligatorias")
    @Min(value = 1, message = "Debe tener al menos 1 hora semanal")
    private Integer horas;

    private String descripcion;

    @NotNull(message = "Debe asignar la materia a un grado")
    private UUID gradoId;

    private String gradoNombre;
    private List<String> docentesNombres = new ArrayList<>();
    private Integer cantidadEvaluaciones = 0;

    public MateriaDTO() {
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public List<String> getDocentesNombres() {
        return docentesNombres;
    }

    public void setDocentesNombres(List<String> docentesNombres) {
        this.docentesNombres = docentesNombres;
    }

    public Integer getCantidadEvaluaciones() {
        return cantidadEvaluaciones;
    }

    public void setCantidadEvaluaciones(Integer cantidadEvaluaciones) {
        this.cantidadEvaluaciones = cantidadEvaluaciones;
    }
}

