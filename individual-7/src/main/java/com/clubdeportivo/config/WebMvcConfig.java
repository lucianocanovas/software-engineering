package com.clubdeportivo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * =========================================================================================
 * CONFIGURACIÓN: WebMvcConfig
 * =========================================================================================
 * Ajustes de Spring MVC para mapeo de vistas directas y exposición de archivos multimedia
 * (fotografías de rostro de socios) almacenadas en el sistema de archivos del servidor.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirige la raíz "/" automáticamente al dashboard principal
        registry.addRedirectViewController("/", "/dashboard");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get("./uploads");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/" + uploadPath + "/");
    }
}

