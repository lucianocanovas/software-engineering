package com.techstore.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * =============================================================================
 * CAPA MODELO / ORM: ENTIDAD Categoria
 * =============================================================================
 * RELACIÓN UML REPRESENTADA: AGREGACIÓN CON Producto (1 agrupa 0..*)
 * -----------------------------------------------------------------------------
 * En el modelado UML, la AGREGACIÓN representa una relación "todo-parte" o de
 * agrupamiento débil donde las partes (los Productos) tienen un ciclo de vida
 * independiente del contenedor (la Categoría).
 *
 * Justificación:
 * Si una Categoría se reorganiza o se elimina, los productos de tecnología
 * no necesariamente deben destruirse: pueden reasignarse a otra categoría o
 * mantenerse desvinculados sin perder su existencia física ni su historial de stock.
 *
 * ANOTACIONES JPA:
 * @Entity: Declara la entidad para Hibernate.
 * @Table(name = "categorias"): Nombre de la tabla física en SQLite.
 * @OneToMany: Mapea la relación 1 a muchos. Nótese que NO se utiliza CascadeType.REMOVE
 *             ni orphanRemoval = true, preservando así la semántica de Agregación.
 */
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false, length = 30)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    /**
     * Relación de Agregación: Una categoría agrupa múltiples productos.
     * mappedBy = "categoria" indica que la clave foránea reside en la tabla 'productos'.
     */
    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private List<Producto> productos = new ArrayList<>();

    public Categoria() {
    }

    public Categoria(String codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // --- Getters y Setters con encapsulamiento ---

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

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public int getCantidadProductos() {
        return productos != null ? productos.size() : 0;
    }
}

