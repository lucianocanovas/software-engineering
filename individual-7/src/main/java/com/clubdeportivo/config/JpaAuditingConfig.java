package com.clubdeportivo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * =========================================================================================
 * CONFIGURACIÓN: JpaAuditingConfig
 * =========================================================================================
 * Habilita y configura la auditoría automática de entidades JPA:
 * - @EnableJpaAuditing: Registra los procesadores de eventos de entidad para @CreatedDate,
 *   @LastModifiedDate, @CreatedBy y @LastModifiedBy.
 * - AuditorAware: Resuelve dinámicamente el nombre de usuario autenticado en la sesión actual
 *   de Spring Security, estampándolo en las columnas creado_por y modificado_por.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
                return Optional.of("SISTEMA_ADMIN");
            }
            return Optional.ofNullable(authentication.getName());
        };
    }
}

