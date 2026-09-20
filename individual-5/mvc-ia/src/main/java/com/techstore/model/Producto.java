package com.techstore.model;

import jakarta.persistence.*;

/**
 * =============================================================================
 * CAPA MODELO / ORM: CLASE ABSTRACTA Producto
 * =============================================================================
 * RELACIÓNES UML REPRESENTADAS:
 * 1. HERENCIA: Superclase abstracta generalizada para productos tecnológicos
 *    que se especializa en ProductoFisico y ProductoDigital.
 * 2. AGREGACIÓN: Participa en la relación con Categoria (0..* productos pertenecen a 1 categoria).
 * 3. ASOCIACIÓN: Es referenciado por DetalleOrdenCompra para compras a proveedores.
 * -----------------------------------------------------------------------------
 * ESTRATEGIA JPA:
 * @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
 * @DiscriminatorColumn(name = "tipo_producto", discriminatorType = DiscriminatorType.STRING)
 *
 * Justificación técnica:
 * SQLite gestiona muy eficientemente consultas polimórficas bajo SINGLE_TABLE,
 * permitiendo listar productos combinados físicos y digitales de manera rápida
 * sin costos de I/O por JOINs múltiples.
 */
@Entity
@Table(name = "productos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_producto", discriminatorType = DiscriminatorType.STRING)
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false, length = 30)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "precio_venta", nullable = false)
    private Double precioVenta;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    /**
     * RELACIÓN DE AGREGACIÓN: Categoria agrupa Productos.
     * @ManyToOne: Varios productos pertenecen a una categoría.
     * @JoinColumn(name = "categoria_id"): Clave foránea en SQLite.
     * Si la categoría se desvincula, el producto sigue existiendo.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = true)
    private Categoria categoria;

    public Producto() {
    }

    public Producto(String codigo, String nombre, String descripcion, Double precioVenta, Categoria categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.categoria = categoria;
        this.activo = true;
    }

    /**
     * MÉTODO POLIMÓRFICO ABSTRACTO exigido por el diagrama de clases:
     * Cada tipo concreto de producto calcula su precio final de forma diferente
     * (por ejemplo, considerando logística/flete para productos físicos o
     * bonificaciones por distribución electrónica en digitales).
     */
    public abstract Double calcularPrecioFinal();

    /**
     * Retorna el tipo de producto legible (FISICO o DIGITAL).
     */
    public abstract String getTipoProducto();

    /**
     * Retorna si el producto gestiona stock físico en inventario.
     */
    public abstract boolean esFisico();

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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}

