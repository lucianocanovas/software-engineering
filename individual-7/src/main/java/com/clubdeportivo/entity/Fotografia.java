package com.clubdeportivo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * =========================================================================================
 * ENTIDAD: Fotografia
 * =========================================================================================
 * Representa la captura biométrica del rostro de una persona registrada en el club deportivo.
 * Se encuentra en relación de COMPOSICIÓN estricta con Persona:
 * - No posee existencia independiente: su ciclo de vida está subordinado a la persona titular.
 * - Al eliminarse una persona, su fotografía asociada se destruye en cascada (CascadeType.ALL y orphanRemoval = true).
 */
@Entity
@Table(name = "fotografias")
public class Fotografia extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ruta relativa, absoluta o URI Data/Base64 del archivo almacenado.
     */
    @Column(name = "ruta_archivo", nullable = false, columnDefinition = "LONGTEXT")
    private String rutaArchivo;

    /**
     * Formato MIME o extensión (image/jpeg, image/png, etc.).
     */
    @Column(name = "formato", length = 30)
    private String formato;

    /**
     * Fecha y hora en que la fotografía fue tomada o cargada.
     */
    @Column(name = "fecha_captura", nullable = false)
    private LocalDateTime fechaCaptura;

    /**
     * Tamaño del archivo en bytes para control de cuotas de almacenamiento.
     */
    @Column(name = "tamanio_bytes")
    private Long tamanioBytes;

    /**
     * Relación inversa de composición con Persona.
     */
    @OneToOne(mappedBy = "fotografia")
    private Persona persona;

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public Fotografia() {
        this.fechaCaptura = LocalDateTime.now();
    }

    public Fotografia(String rutaArchivo, String formato, Long tamanioBytes) {
        this();
        this.rutaArchivo = rutaArchivo;
        this.formato = formato;
        this.tamanioBytes = tamanioBytes;
    }

    // =====================================================================================
    // MÉTODOS DE DOMINIO
    // =====================================================================================

    /**
     * Valida si el archivo fotográfico cumple con los estándares mínimos para reconocimiento facial:
     * - Ruta no nula ni vacía.
     * - Formato admitido (JPEG, PNG, WEBP).
     * - Peso razonable (> 1024 bytes y <= 5MB).
     */
    public boolean validarRostro() {
        if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
            return false;
        }
        if (formato == null || (!formato.toLowerCase().contains("jpeg") &&
                                !formato.toLowerCase().contains("jpg") &&
                                !formato.toLowerCase().contains("png") &&
                                !formato.toLowerCase().contains("webp"))) {
            return false;
        }
        return tamanioBytes != null && tamanioBytes > 1024 && tamanioBytes <= 5 * 1024 * 1024;
    }

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public LocalDateTime getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(LocalDateTime fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public Long getTamanioBytes() {
        return tamanioBytes;
    }

    public void setTamanioBytes(Long tamanioBytes) {
        this.tamanioBytes = tamanioBytes;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
