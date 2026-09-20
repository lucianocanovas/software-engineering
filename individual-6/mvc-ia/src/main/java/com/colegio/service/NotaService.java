package com.colegio.service;

import com.colegio.dto.NotaDTO;
import com.colegio.mapper.NotaMapper;
import com.colegio.model.Alumno;
import com.colegio.model.Evaluacion;
import com.colegio.model.Nota;
import com.colegio.repository.AlumnoRepository;
import com.colegio.repository.EvaluacionRepository;
import com.colegio.repository.NotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * =========================================================================================
 * SERVICIO: NotaService
 * =========================================================================================
 * Gestiona las calificaciones de los alumnos en las evaluaciones.
 * Aplica la regla de negocio de Composición: la Nota se vincula y almacena
 * bajo la pertenencia de una Evaluación concreta.
 */
@Service
public class NotaService {

    private final NotaRepository notaRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final AlumnoRepository alumnoRepository;
    private final NotaMapper notaMapper;

    public NotaService(NotaRepository notaRepository,
                       EvaluacionRepository evaluacionRepository,
                       AlumnoRepository alumnoRepository,
                       NotaMapper notaMapper) {
        this.notaRepository = notaRepository;
        this.evaluacionRepository = evaluacionRepository;
        this.alumnoRepository = alumnoRepository;
        this.notaMapper = notaMapper;
    }

    @Transactional
    public NotaDTO registrarNota(NotaDTO dto) {
        Evaluacion evaluacion = evaluacionRepository.findById(dto.getEvaluacionId())
                .orElseThrow(() -> new IllegalArgumentException("La evaluación especificada no existe."));

        Alumno alumno = alumnoRepository.findById(dto.getAlumnoId())
                .orElseThrow(() -> new IllegalArgumentException("El alumno seleccionado no existe."));

        Nota nota = notaMapper.toEntity(dto, evaluacion, alumno);
        Nota guardada = notaRepository.save(nota);
        return notaMapper.toDTO(guardada);
    }

    @Transactional(readOnly = true)
    public List<NotaDTO> listarTodas() {
        return notaRepository.findAll().stream()
                .map(notaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NotaDTO> listarPorEvaluacion(UUID evaluacionId) {
        return notaRepository.findByEvaluacionId(evaluacionId).stream()
                .map(notaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NotaDTO> listarPorAlumno(UUID alumnoId) {
        return notaRepository.findByAlumnoId(alumnoId).stream()
                .map(notaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Double obtenerPromedioGeneral() {
        Double promedio = notaRepository.calcularPromedioGeneralInstitucional();
        if (promedio == null) {
            return 0.0;
        }
        return Math.round(promedio * 100.0) / 100.0;
    }

    @Transactional(readOnly = true)
    public long contarNotas() {
        return notaRepository.count();
    }
}

