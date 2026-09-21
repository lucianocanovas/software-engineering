package com.clubdeportivo.dto.response;

import com.clubdeportivo.entity.enums.CategoriaSocio;
import java.time.LocalDate;

/**
 * =========================================================================================
 * DTO RESPONSE: SocioResponseDTO
 * =========================================================================================
 * Proyección desacoplada para la visualización de Socios en la interfaz web Thymeleaf.
 * Evita la exposición directa de entidades JPA y de consultas cíclicas o N+1.
 */
public class SocioResponseDTO {

    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String nombreCompleto;
    private int edad;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String email;
    private Boolean activo;
    private String numeroSocio;
    private CategoriaSocio categoria;
    private LocalDate fechaAlta;
    private boolean alDia;
    private String rutaFoto;
    private String codigoGrupoFamiliar;
    private Long grupoFamiliarId;
    private int cantidadAdherentes;

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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public boolean isAlDia() {
        return alDia;
    }

    public void setAlDia(boolean alDia) {
        this.alDia = alDia;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getCodigoGrupoFamiliar() {
        return codigoGrupoFamiliar;
    }

    public void setCodigoGrupoFamiliar(String codigoGrupoFamiliar) {
        this.codigoGrupoFamiliar = codigoGrupoFamiliar;
    }

    public Long getGrupoFamiliarId() {
        return grupoFamiliarId;
    }

    public void setGrupoFamiliarId(Long grupoFamiliarId) {
        this.grupoFamiliarId = grupoFamiliarId;
    }

    public int getCantidadAdherentes() {
        return cantidadAdherentes;
    }

    public void setCantidadAdherentes(int cantidadAdherentes) {
        this.cantidadAdherentes = cantidadAdherentes;
    }
}
