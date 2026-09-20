package com.colegio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO para la gestión y creación de Evaluaciones escolares.
 */
public class EvaluacionDTO {

    private UUID id;

    @NotBlank(message = "El título de la evaluación es obligatorio")
    private String titulo;

    @NotBlank(message = "Debe indicar el tipo de evaluación")
    private String tipo;

    @NotNull(message = "La fecha de la evaluación es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private Double ponderacion;

    @NotNull(message = "Debe seleccionar la materia correspondiente")
    private UUID materiaId;

    private String materiaNombre;
    private UUID profesorId;
    private String profesorNombre;
    private Integer cantidadNotas = 0;

    public EvaluacionDTO() {
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Double ponderacion) {
        this.ponderacion = ponderacion;
    }

    public UUID getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(UUID materiaId) {
        this.materiaId = materiaId;
    }

    public String getMateriaNombre() {
        return materiaNombre;
    }

    public void setMateriaNombre(String materiaNombre) {
        this.materiaNombre = materiaNombre;
    }

    public UUID getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(UUID profesorId) {
        this.profesorId = profesorId;
    }

    public String getProfesorNombre() {
        return profesorNombre;
    }

    public void setProfesorNombre(String profesorNombre) {
        this.profesorNombre = profesorNombre;
    }

    public Integer getCantidadNotas() {
        return cantidadNotas;
    }

    public void setCantidadNotas(Integer cantidadNotas) {
        this.cantidadNotas = cantidadNotas;
    }
}

