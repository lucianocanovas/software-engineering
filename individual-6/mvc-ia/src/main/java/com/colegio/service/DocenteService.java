package com.colegio.service;

import com.colegio.dto.CambioPasswordDTO;
import com.colegio.dto.DocenteDTO;
import com.colegio.dto.DocenteRegistroDTO;
import com.colegio.mapper.DocenteMapper;
import com.colegio.model.Profesor;
import com.colegio.repository.ProfesorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * =========================================================================================
 * CAPA DE SERVICIO: DocenteService
 * =========================================================================================
 * Encapsula la lógica de negocio para la gestión de profesores y docentes.
 *
 * Funcionalidades clave:
 * 1. Registro de docentes con validaciones de unicidad (email y legajo).
 * 2. Cifrado de contraseñas con BCryptPasswordEncoder.
 * 3. Notificación por correo electrónico de bienvenida mediante EmailService.
 * 4. Cambio seguro de contraseña con validación de credencial previa.
 * 5. Transformación estricta a través de DTOs (DocenteMapper).
 */
@Service
public class DocenteService {

    private static final Logger log = LoggerFactory.getLogger(DocenteService.class);

    private final ProfesorRepository profesorRepository;
    private final DocenteMapper docenteMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public DocenteService(ProfesorRepository profesorRepository,
                          DocenteMapper docenteMapper,
                          PasswordEncoder passwordEncoder,
                          EmailService emailService) {
        this.profesorRepository = profesorRepository;
        this.docenteMapper = docenteMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    /**
     * Registra un nuevo docente en el sistema.
     * Valida correspondencia de contraseñas, unicidad de correo y legajo,
     * persiste la entidad y dispara el correo de bienvenida.
     */
    @Transactional
    public DocenteDTO registrarDocente(DocenteRegistroDTO dto) {
        log.info("Iniciando registro de docente con email: {}", dto.getEmail());

        // 1. Validar que las contraseñas coincidan
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden. Por favor verifíquelas.");
        }

        // 2. Validar que el correo no se encuentre registrado previamente
        if (profesorRepository.existsByEmail(dto.getEmail().trim().toLowerCase())) {
            throw new IllegalArgumentException("Ya existe un docente registrado con el correo personal: " + dto.getEmail());
        }

        // 3. Validar unicidad del número de legajo
        if (profesorRepository.existsByLegajo(dto.getLegajo())) {
            throw new IllegalArgumentException("Ya existe un docente con el número de legajo: " + dto.getLegajo());
        }

        // 4. Hashear la contraseña con BCrypt
        String hashPassword = passwordEncoder.encode(dto.getPassword());

        // 5. Mapear DTO a Entidad persistente
        Profesor profesor = docenteMapper.toEntity(dto, hashPassword);

        // 6. Guardar en base de datos SQLite
        Profesor profesorGuardado = profesorRepository.save(profesor);
        log.info("Docente guardado exitosamente con ID: {}", profesorGuardado.getId());

        // 7. Enviar correo de bienvenida al correo personal
        emailService.enviarCorreoBienvenida(
                profesorGuardado.getEmail(),
                profesorGuardado.getNombreCompleto(),
                profesorGuardado.getLegajo()
        );

        // 8. Retornar DTO a la capa superior
        return docenteMapper.toDTO(profesorGuardado);
    }

    /**
     * Realiza el cambio de contraseña para el docente autenticado.
     * Satisface el requisito: "el sistema debe tener la posibilidad de cambiar la contraseña".
     */
    @Transactional
    public void cambiarPassword(String emailDocente, CambioPasswordDTO dto) {
        log.info("Solicitud de cambio de contraseña para el usuario: {}", emailDocente);

        Profesor docente = profesorRepository.findByEmail(emailDocente.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el docente con correo: " + emailDocente));

        // Validar contraseña actual
        if (!passwordEncoder.matches(dto.getPasswordActual(), docente.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual ingresada es incorrecta.");
        }

        // Validar coincidencia de nueva contraseña
        if (!dto.getNuevoPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("La nueva contraseña y su confirmación no coinciden.");
        }

        // Validar que la nueva contraseña sea diferente a la actual
        if (passwordEncoder.matches(dto.getNuevoPassword(), docente.getPassword())) {
            throw new IllegalArgumentException("La nueva contraseña no puede ser idéntica a la contraseña actual.");
        }

        // Encriptar y actualizar
        docente.setPassword(passwordEncoder.encode(dto.getNuevoPassword()));
        profesorRepository.save(docente);
        log.info("Contraseña actualizada exitosamente para el usuario: {}", emailDocente);
    }

    @Transactional(readOnly = true)
    public List<DocenteDTO> listarTodos() {
        return profesorRepository.findAll().stream()
                .map(docenteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DocenteDTO buscarPorEmail(String email) {
        return profesorRepository.findByEmail(email.trim().toLowerCase())
                .map(docenteMapper::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public DocenteDTO buscarPorId(UUID id) {
        return profesorRepository.findById(id)
                .map(docenteMapper::toDTO)
                .orElse(null);
    }
}

