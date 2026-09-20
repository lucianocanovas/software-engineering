package com.colegio.service;

import com.colegio.dto.EvaluacionDTO;
import com.colegio.mapper.EvaluacionMapper;
import com.colegio.model.Evaluacion;
import com.colegio.model.Materia;
import com.colegio.model.Profesor;
import com.colegio.repository.EvaluacionRepository;
import com.colegio.repository.MateriaRepository;
import com.colegio.repository.ProfesorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de Evaluaciones.
 */
@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final MateriaRepository materiaRepository;
    private final ProfesorRepository profesorRepository;
    private final EvaluacionMapper evaluacionMapper;

    public EvaluacionService(EvaluacionRepository evaluacionRepository,
                             MateriaRepository materiaRepository,
                             ProfesorRepository profesorRepository,
                             EvaluacionMapper evaluacionMapper) {
        this.evaluacionRepository = evaluacionRepository;
        this.materiaRepository = materiaRepository;
        this.profesorRepository = profesorRepository;
        this.evaluacionMapper = evaluacionMapper;
    }

    @Transactional
    public EvaluacionDTO crearEvaluacion(EvaluacionDTO dto) {
        Materia materia = materiaRepository.findById(dto.getMateriaId())
                .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));

        Profesor profesor = null;
        if (dto.getProfesorId() != null) {
            profesor = profesorRepository.findById(dto.getProfesorId()).orElse(null);
        }

        Evaluacion evaluacion = evaluacionMapper.toEntity(dto, materia, profesor);
        Evaluacion guardada = evaluacionRepository.save(evaluacion);
        return evaluacionMapper.toDTO(guardada);
    }

    @Transactional(readOnly = true)
    public List<EvaluacionDTO> listarTodas() {
        return evaluacionRepository.findAll().stream()
                .map(evaluacionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EvaluacionDTO buscarPorId(UUID id) {
        return evaluacionRepository.findById(id)
                .map(evaluacionMapper::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public long contarEvaluaciones() {
        return evaluacionRepository.count();
    }
}

