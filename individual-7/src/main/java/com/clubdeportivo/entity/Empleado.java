package com.clubdeportivo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * =========================================================================================
 * ENTIDAD: Empleado
 * =========================================================================================
 * Especialización de Persona mediante HERENCIA (extends Persona):
 * - Representa al personal contratado por el club deportivo (maestranza, recepcionistas, profesores).
 * - Demuestra jerarquía multinivel: Empleado hereda de Persona, y Profesor hereda de Empleado.
 * - Método de dominio:
 *   - registrarAsistencia(): Deja constancia de la presencia laboral del empleado.
 */
@Entity
@Table(name = "empleados")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Empleado extends Persona {

    @Column(name = "legajo", nullable = false, unique = true, length = 30)
    private String legajo;

    @Column(name = "cargo", nullable = false, length = 80)
    private String cargo;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public Empleado() {
        super();
        this.fechaIngreso = LocalDate.now();
    }

    public Empleado(String dni, String nombre, String apellido, LocalDate fechaNacimiento,
                    String telefono, String email, String legajo, String cargo) {
        super(dni, nombre, apellido, fechaNacimiento, telefono, email);
        this.legajo = legajo;
        this.cargo = cargo;
        this.fechaIngreso = LocalDate.now();
    }

    // =====================================================================================
    // MÉTODOS DE NEGOCIO / DOMINIO
    // =====================================================================================

    /**
     * Registra la asistencia laboral y valida que el empleado se encuentre activo.
     */
    public void registrarAsistencia() {
        if (!Boolean.TRUE.equals(this.getActivo())) {
            throw new IllegalStateException("No se puede registrar asistencia de un empleado inactivo");
        }
    }

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
