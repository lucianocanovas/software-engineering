package com.colegio.service;

import com.colegio.model.Persona;
import com.colegio.repository.PersonaRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * =========================================================================================
 * SERVICIO DE AUTENTICACIÓN: CustomUserDetailsService
 * =========================================================================================
 * Implementa la interfaz estándar UserDetailsService de Spring Security.
 * Localiza las credenciales del docente o usuario en la base de datos a través de su
 * correo electrónico personal ("El usuario es el correo personal del docente").
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonaRepository personaRepository;

    public CustomUserDetailsService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Persona persona = personaRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + email));

        return new User(
                persona.getEmail(),
                persona.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(persona.getRol().name()))
        );
    }
}

