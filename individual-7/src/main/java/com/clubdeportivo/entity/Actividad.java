package com.clubdeportivo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================================================
 * ENTIDAD: Actividad
 * =========================================================================================
 * Representa las disciplinas deportivas y formativas impartidas en el club (Natación,
 * Tenis, Fútbol, Gimnasia Artística, Básquetbol):
 * - Demuestra relación de AGREGACIÓN con Profesor: Uno o más profesores dictan la actividad,
 *   pero ambos subsisten de forma independiente.
 * - Demuestra relación de ASOCIACIÓN con Persona/Socio: Los socios se inscriben en actividades.
 * - Métodos de dominio:
 *   - hayCupo(): Verifica si la cantidad de inscriptos no supera el cupo máximo reglamentario.
 *   - inscribir(Persona): Registra a un socio o adherente en la disciplina si hay cupo disponible.
 */
@Entity
@Table(name = "actividades")
public class Actividad extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 30)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "dias_horarios", nullable = false, length = 150)
    private String diasHorarios;

    @Column(name = "cupo_maximo", nullable = false)
    private Integer cupoMaximo;

    /**
     * Profesores asignados para dictar la disciplina (Relación ManyToMany).
     */
    @ManyToMany(mappedBy = "actividades", fetch = FetchType.LAZY)
    private List<Profesor> profesores = new ArrayList<>();

    /**
     * Personas (Socios / Adherentes) inscriptas formalmente en la actividad.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "actividad_inscriptos",
        joinColumns = @JoinColumn(name = "actividad_id"),
        inverseJoinColumns = @JoinColumn(name = "persona_id")
    )
    private List<Persona> inscriptos = new ArrayList<>();

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public Actividad() {
    }

    public Actividad(String codigo, String nombre, String diasHorarios, Integer cupoMaximo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.diasHorarios = diasHorarios;
        this.cupoMaximo = cupoMaximo;
    }

    // =====================================================================================
    // MÉTODOS DE NEGOCIO / DOMINIO
    // =====================================================================================

    /**
     * Evalúa si aún existen vacantes disponibles para nuevos inscriptos.
     */
    public boolean hayCupo() {
        int cantidadActual = (this.inscriptos != null) ? this.inscriptos.size() : 0;
        return cantidadActual < this.cupoMaximo;
    }

    /**
     * Inscribe a una persona en la actividad si se verifica cupo y no estaba inscripta previamente.
     */
    public boolean inscribir(Persona persona) {
        if (!hayCupo() || persona == null) {
            return false;
        }
        if (!this.inscriptos.contains(persona)) {
            this.inscriptos.add(persona);
            return true;
        }
        return false;
    }

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDiasHorarios() {
        return diasHorarios;
    }

    public void setDiasHorarios(String diasHorarios) {
        this.diasHorarios = diasHorarios;
    }

    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public List<Persona> getInscriptos() {
        return inscriptos;
    }

    public void setInscriptos(List<Persona> inscriptos) {
        this.inscriptos = inscriptos;
    }
}
