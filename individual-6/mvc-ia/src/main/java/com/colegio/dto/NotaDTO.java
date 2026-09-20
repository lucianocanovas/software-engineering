package com.colegio.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO para el registro, edición y visualización de Calificaciones (Notas).
 */
public class NotaDTO {

    private UUID id;

    @NotNull(message = "Debe especificar la evaluación")
    private UUID evaluacionId;

    private String evaluacionTitulo;
    private String materiaNombre;

    @NotNull(message = "Debe seleccionar el alumno a calificar")
    private UUID alumnoId;

    private String alumnoNombre;
    private Integer alumnoCodigo;

    @NotNull(message = "El puntaje es obligatorio")
    @DecimalMin(value = "0.0", message = "La nota mínima es 0.0")
    @DecimalMax(value = "10.0", message = "La nota máxima es 10.0")
    private Double puntaje;

    @NotNull(message = "La fecha de registro es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private String observaciones;
    private boolean aprobada;

    public NotaDTO() {
        this.fecha = LocalDate.now();
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEvaluacionId() {
        return evaluacionId;
    }

    public void setEvaluacionId(UUID evaluacionId) {
        this.evaluacionId = evaluacionId;
    }

    public String getEvaluacionTitulo() {
        return evaluacionTitulo;
    }

    public void setEvaluacionTitulo(String evaluacionTitulo) {
        this.evaluacionTitulo = evaluacionTitulo;
    }

    public String getMateriaNombre() {
        return materiaNombre;
    }

    public void setMateriaNombre(String materiaNombre) {
        this.materiaNombre = materiaNombre;
    }

    public UUID getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(UUID alumnoId) {
        this.alumnoId = alumnoId;
    }

    public String getAlumnoNombre() {
        return alumnoNombre;
    }

    public void setAlumnoNombre(String alumnoNombre) {
        this.alumnoNombre = alumnoNombre;
    }

    public Integer getAlumnoCodigo() {
        return alumnoCodigo;
    }

    public void setAlumnoCodigo(Integer alumnoCodigo) {
        this.alumnoCodigo = alumnoCodigo;
    }

    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }
}

