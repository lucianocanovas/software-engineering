package com.clubdeportivo.config;

import com.clubdeportivo.entity.Usuario;
import com.clubdeportivo.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

/**
 * =========================================================================================
 * CONFIGURACIÓN: SecurityConfig (Spring Security 6)
 * =========================================================================================
 * Define las políticas de seguridad, autenticación, autorización y protección CSRF:
 * - BCryptPasswordEncoder: Cifrado unidireccional con salting para las contraseñas.
 * - UserDetailsService: Consulta de credenciales persistidas en la tabla 'usuarios'.
 * - SecurityFilterChain: Filtros HTTP para proteger endpoints según roles (ADMIN, OPERADOR, SOCIO).
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return username -> {
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

            if (!Boolean.TRUE.equals(usuario.getActivo())) {
                throw new UsernameNotFoundException("La cuenta de usuario se encuentra inactiva");
            }

            return new User(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().name()))
            );
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Recursos estáticos públicos (CSS, JS, imágenes, webjars)
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico", "/h2-console/**").permitAll()
                // Página de inicio de sesión pública
                .requestMatchers("/login").permitAll()
                // Acciones exclusivas de Administrador
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Todo el resto requiere usuario autenticado (ADMIN, OPERADOR o SOCIO)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf
                // Permitir consola H2 en desarrollo si está activa
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }
}

