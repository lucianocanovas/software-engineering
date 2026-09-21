package com.clubdeportivo.dto.request;

import com.clubdeportivo.entity.enums.CategoriaSocio;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

/**
 * =========================================================================================
 * DTO REQUEST: SocioRequestDTO
 * =========================================================================================
 * Objeto de transferencia para la creación y edición de Socios Titulares.
 * Contiene validaciones declarativas Bean Validation (JSR 380) para desacoplar el contrato
 * de la vista web del modelo de persistencia ORM.
 */
public class SocioRequestDTO {

    private Long id;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{7,10}", message = "El DNI debe contener entre 7 y 10 dígitos numéricos")
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

    @Pattern(regexp = "^$|[0-9+() -]{6,25}", message = "Formato de teléfono no válido")
    private String telefono;

    @Email(message = "Formato de correo electrónico inválido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String email;

    @NotBlank(message = "El número de socio es obligatorio")
    private String numeroSocio;

    @NotNull(message = "La categoría de socio es obligatoria")
    private CategoriaSocio categoria = CategoriaSocio.ACTIVO;

    /**
     * Fotografía del rostro en formato Base64 o URL cargada desde la interfaz.
     */
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

    public String getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(String numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public CategoriaSocio getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaSocio categoria) {
        this.categoria = categoria;
    }

    public String getFotoData() {
        return fotoData;
    }

    public void setFotoData(String fotoData) {
        this.fotoData = fotoData;
    }
}
