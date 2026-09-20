package com.colegio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * =========================================================================================
 * CONFIGURACIÓN DE AUDITORÍA: JpaAuditingConfig
 * =========================================================================================
 * Habilita la auditoría automática de entidades JPA (@EnableJpaAuditing).
 * Define el proveedor AuditorAware para capturar qué usuario realizó cada operación.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    /**
     * Implementación de AuditorAware para extraer el correo o username del usuario autenticado
     * en el SecurityContext de Spring Security.
     */
    public static class AuditorAwareImpl implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || "anonymousUser".equals(authentication.getPrincipal())) {
                return Optional.of("SISTEMA_AUTO");
            }

            return Optional.ofNullable(authentication.getName());
        }
    }
}

