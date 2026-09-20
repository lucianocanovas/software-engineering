package com.techstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * =============================================================================
 * CLASE PRINCIPAL: TechStoreApplication
 * =============================================================================
 * Punto de entrada de la aplicación Spring Boot.
 *
 * Anotaciones:
 * @SpringBootApplication:
 *   - @Configuration: Habilita la definición de Beans de contexto.
 *   - @EnableAutoConfiguration: Configura automáticamente DataSource SQLite, JPA y Thymeleaf.
 *   - @ComponentScan: Escanea los paquetes hijos en busca de @Controller, @Service, @Repository y @Component.
 */
@SpringBootApplication
public class TechStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechStoreApplication.class, args);
        System.out.println("=================================================================");
        System.out.println("  TechStore MVC - Sistema de Gestión Tecnológica Iniciado");
        System.out.println("  URL: http://localhost:8080");
        System.out.println("  Credenciales demo:");
        System.out.println("    - Admin:    admin / admin123");
        System.out.println("    - Compras:  compras / compras123");
        System.out.println("    - Vendedor: vendedor / vendedor123");
        System.out.println("=================================================================");
    }
}

