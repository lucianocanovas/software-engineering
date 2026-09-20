package com.colegio.dto;

import com.colegio.model.Sexo;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * =========================================================================================
 * DTO DE TRANSFERENCIA: DocenteRegistroDTO
 * =========================================================================================
 * Encapsula la información enviada desde el formulario de registro de un nuevo docente.
 * Cumple con la exigencia explícita del ejercicio:
 * - "los docentes se registran con 'Nombre', 'Apellido', 'Sexo' y 'Fecha de Nacimiento'.
 *    El usuario es el correo personal del docente".
 *
 * Cuenta con validaciones declarativas mediante Jakarta Validation (@NotBlank, @Email, @Past, etc.).
 */
public class DocenteRegistroDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 80, message = "El nombre debe tener entre 2 y 80 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 80, message = "El apellido debe tener entre 2 y 80 caracteres")
    private String apellido;

    @NotNull(message = "Debe seleccionar el sexo")
    private Sexo sexo;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    /**
     * Correo personal que actúa como usuario del docente para autenticación.
     */
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe proporcionar un formato de correo válido")
    private String email;

    @NotBlank(message = "El número de DNI es obligatorio")
    @Pattern(regexp = "\\d{7,10}", message = "El DNI debe contener entre 7 y 10 dígitos numéricos")
    private String dni;

    @NotNull(message = "El número de legajo es obligatorio")
    @Min(value = 1, message = "El legajo debe ser un entero positivo")
    private Integer legajo;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @PositiveOrZero(message = "El salario debe ser un valor positivo o cero")
    private Double salario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotBlank(message = "Debe confirmar la contraseña")
    private String confirmPassword;

    public DocenteRegistroDTO() {
    }

    // Getters y Setters
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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getLegajo() {
        return legajo;
    }

    public void setLegajo(Integer legajo) {
        this.legajo = legajo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

