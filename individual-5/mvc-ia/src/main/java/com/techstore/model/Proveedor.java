package com.techstore.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * =============================================================================
 * CAPA MODELO / ORM: ENTIDAD Proveedor
 * =============================================================================
 * RELACIÓN UML REPRESENTADA: ASOCIACIÓN (0..* emitida a 1)
 * -----------------------------------------------------------------------------
 * Modela a los fabricantes y distribuidores mayoristas de tecnología
 * (ej: ASUS, Kingston, Logitech, Microsoft) a quienes la empresa les compra
 * mercadería mediante la emisión de Órdenes de Compra.
 *
 * La relación de ASOCIACIÓN vincula semánticamente dos entidades independientes
 * que colaboran en el flujo de negocio comercial.
 */
@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false, length = 30)
    private String codigo;

    @Column(name = "razon_social", nullable = false, length = 150)
    private String razonSocial;

    @Column(name = "cuit", unique = true, nullable = false, length = 20)
    private String cuit;

    @Column(name = "telefono", length = 30)
    private String telefono;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    /**
     * Relación de Asociación con OrdenDeCompra: Un proveedor puede recibir múltiples órdenes.
     */
    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    private List<OrdenDeCompra> ordenesCompra = new ArrayList<>();

    public Proveedor() {
    }

    public Proveedor(String codigo, String razonSocial, String cuit, String telefono, String email, String direccion) {
        this.codigo = codigo;
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.activo = true;
    }

    // --- Getters y Setters ---

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

    public List<OrdenDeCompra> getOrdenesCompra() {
        return ordenesCompra;
    }

    public void setOrdenesCompra(List<OrdenDeCompra> ordenesCompra) {
        this.ordenesCompra = ordenesCompra;
    }
}

