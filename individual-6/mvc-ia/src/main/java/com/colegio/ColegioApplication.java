package com.colegio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * =========================================================================================
 * CLASE PRINCIPAL: ColegioApplication
 * =========================================================================================
 * Punto de entrada del Sistema de Gestión Escolar con Arquitectura MVC.
 *
 * Características implementadas:
 * - Capa Web: Spring MVC y Thymeleaf.
 * - Estilos: Bootstrap 5 responsivo (ThemeWagon style).
 * - Persistencia: ORM con Spring Data JPA, Hibernate y base de datos relacional SQLite3.
 * - Desacoplamiento: Capa DTO estricta entre presentación y persistencia.
 * - Auditoría: JPA Auditing en entidades (creación, modificación, autor).
 * - Seguridad: Spring Security con BCrypt, sesiones y cambio de contraseña.
 * - Notificaciones: Servicio de correo de bienvenida al docente en su registro.
 * - Modelado UML: Cumplimiento de Herencia, Composición, Agregación y Asociación.
 */
@SpringBootApplication
public class ColegioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColegioApplication.class, args);
    }
}
