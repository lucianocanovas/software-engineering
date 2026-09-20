package com.techstore.dto;

import jakarta.validation.constraints.*;

/**
 * =============================================================================
 * CAPA DTO: ProductoDTO
 * =============================================================================
 * Transfiere los datos de productos tecnológicos entre la capa de Controladores
 * y las vistas Thymeleaf, manteniendo aisladas las entidades JPA de la base de datos.
 *
 * Soporta la representación unificada y validada tanto de productos Físicos
 * (inventario en bodega) como Digitales (software y licencias).
 */
public class ProductoDTO {

    private Long id;

    @NotBlank(message = "El código es obligatorio")
    @Size(min = 2, max = 30, message = "El código debe tener entre 2 y 30 caracteres")
    private String codigo;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 3, max = 120, message = "El nombre debe tener entre 3 y 120 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private Double precioVenta;

    // Calculado polimórficamente por el modelo de dominio
    private Double precioFinal;

    private Boolean activo = true;

    @NotNull(message = "Debe seleccionar una categoría")
    private Long categoriaId;

    private String categoriaNombre;

    @NotBlank(message = "Debe especificar el tipo de producto (FISICO o DIGITAL)")
    private String tipoProducto; // "FISICO" o "DIGITAL"

    // --- Atributos específicos para Producto Físico ---
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock = 0;

    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimo = 5;

    @Min(value = 0, message = "El peso debe ser positivo")
    private Double peso = 0.0;

    private String ubicacionDeposito;

    private boolean bajoStock;

    // --- Atributos específicos para Producto Digital ---
    private String claveLicencia;

    private String urlDescarga;

    @Min(value = 1, message = "La vigencia mínima es de 1 día")
    private Integer vigenciaDias = 365;

    public ProductoDTO() {
    }

    public boolean esFisico() {
        return "FISICO".equalsIgnoreCase(tipoProducto);
    }

    public boolean esDigital() {
        return "DIGITAL".equalsIgnoreCase(tipoProducto);
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(Double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getUbicacionDeposito() {
        return ubicacionDeposito;
    }

    public void setUbicacionDeposito(String ubicacionDeposito) {
        this.ubicacionDeposito = ubicacionDeposito;
    }

    public boolean isBajoStock() {
        return bajoStock;
    }

    public void setBajoStock(boolean bajoStock) {
        this.bajoStock = bajoStock;
    }

    public String getClaveLicencia() {
        return claveLicencia;
    }

    public void setClaveLicencia(String claveLicencia) {
        this.claveLicencia = claveLicencia;
    }

    public String getUrlDescarga() {
        return urlDescarga;
    }

    public void setUrlDescarga(String urlDescarga) {
        this.urlDescarga = urlDescarga;
    }

    public Integer getVigenciaDias() {
        return vigenciaDias;
    }

    public void setVigenciaDias(Integer vigenciaDias) {
        this.vigenciaDias = vigenciaDias;
    }
}

