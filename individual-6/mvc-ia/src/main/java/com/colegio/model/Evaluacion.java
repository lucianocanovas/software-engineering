package com.colegio.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * =========================================================================================
 * ENTIDAD: Evaluacion
 * =========================================================================================
 * Representa una instancia formal de examen, parcial o trabajo práctico.
 *
 * RELACIONES UML IMPLEMENTADAS:
 * 1. Composición con Nota (1 Evaluacion contiene 1..* Notas):
 *    Una Nota NO TIENE SENTIDO NI EXISTENCIA autónoma fuera de la evaluación que califica.
 *    Se modela con CascadeType.ALL y orphanRemoval = true: eliminar la evaluación destruye
 *    automáticamente todas las calificaciones de los alumnos asociadas a dicho examen.
 * 2. Asociación con Materia y Profesor: La evaluación es planificada por un profesor para su materia.
 */
@Entity
@Table(name = "evaluaciones")
public class Evaluacion extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "titulo", nullable = false, length = 120)
    private String titulo;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo; // "Examen Parcial", "Trabajo Práctico", "Examen Final"

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "ponderacion")
    private Double ponderacion; // Ej: 1.0 (100%), 0.3 (30%)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    /**
     * COMPOSICIÓN UML:
     * Una Evaluación es dueña composicional de sus Notas.
     * orphanRemoval = true garantiza la dependencia de ciclo de vida.
     */
    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();

    public Evaluacion() {
    }

    public Evaluacion(String titulo, String tipo, LocalDate fecha, Double ponderacion, Materia materia, Profesor profesor) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.fecha = fecha;
        this.ponderacion = ponderacion;
        this.materia = materia;
        this.profesor = profesor;
    }

    public void agregarNota(Nota nota) {
        notas.add(nota);
        nota.setEvaluacion(this);
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

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }
}

