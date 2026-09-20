package com.colegio.service;

import com.colegio.dto.AlumnoDTO;
import com.colegio.mapper.AlumnoMapper;
import com.colegio.model.Alumno;
import com.colegio.model.Aula;
import com.colegio.repository.AlumnoRepository;
import com.colegio.repository.AulaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio de negocio para la administración de Alumnos.
 */
@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final AulaRepository aulaRepository;
    private final AlumnoMapper alumnoMapper;
    private final PasswordEncoder passwordEncoder;

    public AlumnoService(AlumnoRepository alumnoRepository,
                         AulaRepository aulaRepository,
                         AlumnoMapper alumnoMapper,
                         PasswordEncoder passwordEncoder) {
        this.alumnoRepository = alumnoRepository;
        this.aulaRepository = aulaRepository;
        this.alumnoMapper = alumnoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AlumnoDTO registrarAlumno(AlumnoDTO dto) {
        if (alumnoRepository.existsByCodigo(dto.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un alumno con la matrícula/código: " + dto.getCodigo());
        }

        Aula aula = null;
        if (dto.getAulaId() != null) {
            aula = aulaRepository.findById(dto.getAulaId())
                    .orElseThrow(() -> new IllegalArgumentException("El aula seleccionada no existe."));
        }

        String defaultPass = passwordEncoder.encode("alumno123");
        Alumno alumno = alumnoMapper.toEntity(dto, aula, defaultPass);
        Alumno guardado = alumnoRepository.save(alumno);
        return alumnoMapper.toDTO(guardado);
    }

    @Transactional(readOnly = true)
    public List<AlumnoDTO> listarTodos() {
        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlumnoDTO buscarPorId(UUID id) {
        return alumnoRepository.findById(id)
                .map(alumnoMapper::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<AlumnoDTO> listarPorAula(UUID aulaId) {
        return alumnoRepository.findByAulaId(aulaId).stream()
                .map(alumnoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long contarAlumnos() {
        return alumnoRepository.count();
    }
}

