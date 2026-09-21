package com.clubdeportivo.dto.response;

import com.clubdeportivo.entity.enums.Parentesco;
import java.time.LocalDate;

/**
 * =========================================================================================
 * DTO RESPONSE: FamiliarAdherenteResponseDTO
 * =========================================================================================
 * Proyección desacoplada para familiares adherentes vinculados a un grupo familiar.
 */
public class FamiliarAdherenteResponseDTO {

    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String nombreCompleto;
    private int edad;
    private LocalDate fechaNacimiento;
    private boolean menorDeEdad;
    private String telefono;
    private String email;
    private Parentesco parentesco;
    private String rutaFoto;
    private Long grupoFamiliarId;
    private String grupoFamiliarCodigo;
    private String titularNombre;

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isMenorDeEdad() {
        return menorDeEdad;
    }

    public void setMenorDeEdad(boolean menorDeEdad) {
        this.menorDeEdad = menorDeEdad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public Long getGrupoFamiliarId() {
        return grupoFamiliarId;
    }

    public void setGrupoFamiliarId(Long grupoFamiliarId) {
        this.grupoFamiliarId = grupoFamiliarId;
    }

    public String getGrupoFamiliarCodigo() {
        return grupoFamiliarCodigo;
    }

    public void setGrupoFamiliarCodigo(String grupoFamiliarCodigo) {
        this.grupoFamiliarCodigo = grupoFamiliarCodigo;
    }

    public String getTitularNombre() {
        return titularNombre;
    }

    public void setTitularNombre(String titularNombre) {
        this.titularNombre = titularNombre;
    }
}
