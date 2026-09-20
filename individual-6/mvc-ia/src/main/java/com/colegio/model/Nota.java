package com.colegio.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * =========================================================================================
 * ENTIDAD: Nota (Calificación)
 * =========================================================================================
 * Representa la calificación numérica obtenida por un alumno en una evaluación específica.
 *
 * RELACIONES UML IMPLEMENTADAS:
 * 1. Composición con Evaluacion: La nota pertenece existencialmente a una evaluación.
 * 2. Asociación con Alumno: Cada nota individualiza el puntaje alcanzado por un estudiante concreto.
 */
@Entity
@Table(name = "notas")
public class Nota extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "puntaje", nullable = false)
    private Double puntaje;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "observaciones", length = 255)
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluacion_id", nullable = false)
    private Evaluacion evaluacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    public Nota() {
    }

    public Nota(Double puntaje, LocalDate fecha, String observaciones, Evaluacion evaluacion, Alumno alumno) {
        this.puntaje = puntaje;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.evaluacion = evaluacion;
        this.alumno = alumno;
    }

    /**
     * Regla de negocio de dominio: determina si la nota es aprobatoria (>= 6.0).
     */
    public boolean isAprobada() {
        return puntaje != null && puntaje >= 6.0;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}

