package com.clubdeportivo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * =========================================================================================
 * CLASE PRINCIPAL: ClubDeportivoApplication
 * =========================================================================================
 * Punto de entrada de la aplicación Spring Boot para el Club Deportivo.
 *
 * Características Arquitectónicas:
 * - Arquitectura MVC (Model - View - Controller).
 * - Vistas renderizadas del lado del servidor con Thymeleaf y Bootstrap 5.
 * - Capa de Persistencia y ORM con Hibernate / Spring Data JPA.
 * - Soporte nativo para MySQL (y modo fallback H2 para pruebas automatizadas).
 * - Transferencia estricta de datos mediante DTOs (Data Transfer Objects).
 * - Auditoría automática de entidades (AuditableEntity con JPA Auditing).
 * - Seguridad integral con Spring Security (roles RBAC y contraseñas BCrypt).
 * - Módulo de pagos multicanal (Efectivo, Transferencia, Mercado Pago) para cuotas familiares.
 * - Registro biométrico y control de acceso en molinetes con cómputo de permanencia.
 */
@SpringBootApplication
public class ClubDeportivoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubDeportivoApplication.class, args);
    }
}

