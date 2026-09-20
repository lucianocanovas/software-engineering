package com.colegio.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * =========================================================================================
 * ENTIDAD: Materia (Asignatura)
 * =========================================================================================
 * Representa la materia o cátedra académica (ej. Matemáticas, Historia, Lengua, Física).
 *
 * RELACIONES UML IMPLEMENTADAS:
 * 1. Asociación con Profesor: Una materia es dictada por profesores calificados.
 * 2. Asociación con Grado: Una materia forma parte de la currícula de un grado.
 * 3. Asociación con Evaluacion: Una materia contiene los exámenes y trabajos evaluativos.
 */
@Entity
@Table(name = "materias")
public class Materia extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "horas", nullable = false)
    private Integer horas;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id")
    private Grado grado;

    @ManyToMany(mappedBy = "materias")
    private List<Profesor> profesores = new ArrayList<>();

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluacion> evaluaciones = new ArrayList<>();

    public Materia() {
    }

    public Materia(String nombre, Integer horas, String descripcion, Grado grado) {
        this.nombre = nombre;
        this.horas = horas;
        this.descripcion = descripcion;
        this.grado = grado;
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

    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }
}

