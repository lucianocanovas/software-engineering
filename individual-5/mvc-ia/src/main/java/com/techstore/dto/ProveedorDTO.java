package com.techstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * =============================================================================
 * CAPA DTO: ProveedorDTO
 * =============================================================================
 * Objeto de transferencia para proveedores mayoristas.
 */
public class ProveedorDTO {

    private Long id;

    @NotBlank(message = "El código de proveedor es obligatorio")
    @Size(min = 2, max = 30, message = "El código debe tener entre 2 y 30 caracteres")
    private String codigo;

    @NotBlank(message = "La razón social es obligatoria")
    @Size(min = 3, max = 150, message = "La razón social debe tener entre 3 y 150 caracteres")
    private String razonSocial;

    @NotBlank(message = "El CUIT es obligatorio")
    @Size(min = 10, max = 20, message = "El CUIT debe tener entre 10 y 20 caracteres")
    private String cuit;

    private String telefono;

    @Email(message = "Formato de email inválido")
    private String email;

    private String direccion;

    private Boolean activo = true;

    private int cantidadOrdenes;

    public ProveedorDTO() {
    }

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

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public int getCantidadOrdenes() {
        return cantidadOrdenes;
    }

    public void setCantidadOrdenes(int cantidadOrdenes) {
        this.cantidadOrdenes = cantidadOrdenes;
    }
}

