package com.clubdeportivo.dto.request;

import com.clubdeportivo.entity.enums.Parentesco;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

/**
 * =========================================================================================
 * DTO REQUEST: FamiliarAdherenteRequestDTO
 * =========================================================================================
 * Contrato de entrada para incorporar adherentes a un grupo familiar existente.
 */
public class FamiliarAdherenteRequestDTO {

    private Long id;

    @NotNull(message = "Debe especificar el socio titular o grupo familiar")
    private Long socioTitularId;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{7,10}", message = "El DNI debe contener entre 7 y 10 dígitos")
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 80, message = "El nombre debe tener entre 2 y 80 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 80, message = "El apellido debe tener entre 2 y 80 caracteres")
    private String apellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    private String telefono;
    private String email;

    @NotNull(message = "El parentesco con el socio titular es obligatorio")
    private Parentesco parentesco = Parentesco.HIJO;

    private String fotoData;

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSocioTitularId() {
        return socioTitularId;
    }

    public void setSocioTitularId(Long socioTitularId) {
        this.socioTitularId = socioTitularId;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getFotoData() {
        return fotoData;
    }

    public void setFotoData(String fotoData) {
        this.fotoData = fotoData;
    }
}
