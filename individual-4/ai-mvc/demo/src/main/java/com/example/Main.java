package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicacion.
 *
 * La anotacion {@code @SpringBootApplication} combina tres responsabilidades:
 * configura Spring Boot, habilita el autodescubrimiento de componentes y
 * activa la configuracion automatica de MVC, JPA y Thymeleaf.
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}